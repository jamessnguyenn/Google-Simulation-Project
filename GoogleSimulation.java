package pa01;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * This class creates a Google Simulator in which users are able to search a
 * specific keyword, and the class will create a queue and list in which users
 * are able to view the list, and edit the queue.
 * 
 * @author - James Nguyen
 *
 */
public class GoogleSimulation extends JFrame implements ActionListener {
	WebCrawler searcher; // crawler which will use a Google Bot and crawl the web to search for the
							// specific keyword
	Url[] linkList; // list which will contain the 30 links that the crawler found
	Url[] linkQueue; // list which will be the heap queue
	HeapEditor heapQueue; // can only for linkQueue since it contains builds LinkQueue's heap, and
	// contains it's heapsize

	/**
	 * Constructor which will first ask the user for the keyword, and then lead the
	 * user into a window which contains a menu for the user edit the queue, or view
	 * aspects of the list
	 */
	public GoogleSimulation() {
		super("What do you want to do?");
		this.setSize(750, 500);
		this.setLayout(new GridLayout(5, 2));
		String keyword = JOptionPane.showInputDialog("Search the web by entering a keyword"); // ask the user for a
																								// keyword
		if (keyword == null) {
			System.exit(0);
		}
		searcher = new WebCrawler(keyword); // the searcher then searches for the keyword
		searcher.search();
		linkList = new Url[30]; // Initiates link list and linkqueue
		linkQueue = new Url[30];
		ArrayList<String> urlList = new ArrayList<String>(searcher.getUrls()); // creates an array list of the URLS that
																				// are found
		if (urlList.isEmpty()) { // if the search is not found, then return a no search found message
			JFrame noPage = new JFrame();
			noPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			noPage.setSize(500, 250);
			JLabel noSearch = new JLabel("No Search Found. Try Again", JLabel.CENTER);
			noPage.add(noSearch);
			noPage.setVisible(true);
		} else {
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// creating the different buttons within the menu
			Button viewLink = new Button("View the 30 Web Links");
			Button viewScore = new Button("View the Four Factor Rank Score of each Link");
			Button viewRank = new Button("View the 30 Web Links In Order based on its PageRank");
			// Heap Priority Queue
			Button viewTop = new Button("View the Top Link of the Queue");
			Button increaseKey = new Button("Increase the Page Rank of a Link Within the Queue");
			Button insert = new Button("Insert a Web Link Within the Queue");
			Button viewQueue = new Button("View the Links Within the Queue");
			Button exit = new Button("Exit Window");
			// creates actionListeners to allow actions to take place if the buttons are
			// pressed
			viewLink.addActionListener(this);
			viewScore.addActionListener(this);
			viewRank.addActionListener(this);
			viewTop.addActionListener(this);
			increaseKey.addActionListener(this);
			insert.addActionListener(this);
			exit.addActionListener(this);
			viewQueue.addActionListener(this);
			// sets a command that will be sent if the buttons are pressed
			viewLink.setActionCommand("viewLink");
			viewScore.setActionCommand("viewScore");
			viewRank.setActionCommand("viewRank");
			viewTop.setActionCommand("viewTop");
			increaseKey.setActionCommand("increaseKey");
			insert.setActionCommand("insert");
			exit.setActionCommand("exit");
			viewQueue.setActionCommand("viewQueue");
			// creates 30 URL objects and adds it into the the UrlList
			for (int i = 0; i < 30; i++) {
				int freqNum = (int) (Math.random() * 100) + 1;
				int existNum = (int) (Math.random() * 100) + 1;
				int linkNum = (int) (Math.random() * 100) + 1;
				int adverNum = (int) (Math.random() * 100) + 1;
				String url = urlList.get(i);
				if (urlList.get(i).indexOf('/') != -1) {
					url = urlList.get(i).substring(0, urlList.get(i).indexOf('/'));

				}
				Url link = new Url(url, freqNum, existNum, linkNum, adverNum);
				linkList[i] = link;


			}
			HeapEditor temp = new HeapEditor();
			Url[] copy  = linkList;
			temp.heapSorter(copy);
			int j =29;
			// copies the top 20 Urls into the linkQueue
			for (int i = 0; i < 20; i++) {
				linkQueue[i] = new Url(copy[j].getUrl(), copy[j].getfreqNum(), copy[j].getexistNum(),
						copy[j].getlinkNum(), copy[j].getadverNum());
				j--;
			}
			heapQueue = new HeapEditor();
			heapQueue.buildMaxHeap(linkQueue); // build a maxheap with the 20 links in the LinkQueue

			// adds the label and button
			this.add(new JLabel("Link List", JLabel.CENTER));
			this.add(new JLabel("Link Queue (Seperate from Link List)", JLabel.CENTER));
			this.add(viewLink);
			this.add(viewQueue);
			this.add(viewScore);
			this.add(increaseKey);
			this.add(viewRank);
			this.add(viewTop);
			this.add(exit);
			this.add(insert);

			this.setVisible(true);
		}

	}

	@Override
	/**
	 * method which will listen and perform the event if one of the buttons send an
	 * action command
	 */
	public void actionPerformed(ActionEvent pushed) {
		String event = pushed.getActionCommand();
		// if the users clicks onto view links
		if (event.equals("viewLink")) {
			// creates a window which will have the 30 links within the link List
			JFrame viewWindow = new JFrame("View Links");
			viewWindow.setVisible(true);
			viewWindow.setSize(500, 500);
			viewWindow.setLayout(new GridLayout(31, 1));
			viewWindow.add(new JLabel("The first 30 links are as followed:", JLabel.CENTER));
			for (int i = 0; i < linkList.length; i++) {
				viewWindow.add(new Label((i + 1 + ". " + linkList[i].getUrl())));
			}
		}
		// if the user clicks onto viewing the four factor score of a specfic link
		if (event.equals("viewScore")) {
			JFrame scoreWindow = new JFrame("View Score");
			// a score window is created to show the users the differnt links in the list
			scoreWindow.setSize(500, 500);
			scoreWindow.setLayout(new GridLayout(35, 1));
			scoreWindow.add(new JLabel("The first 30 links are as followed:", JLabel.CENTER));
			for (int i = 0; i < linkList.length; i++) {
				scoreWindow.add(new Label((i + 1 + ". " + linkList[i].getUrl())));
			}
			scoreWindow.setVisible(true);
			String integerString;
			// another window is created to ask the user which link the user would like to
			// view by asking the user to enter the corresponding number of the link
			JFrame enterInt = new JFrame();
			enterInt.setSize(500, 250);
			JLabel noSearch = new JLabel("Enter a valid integer between 1 and 30. Try again", JLabel.CENTER);
			enterInt.add(noSearch);
			enterInt.setLocation((int) enterInt.getLocation().getX(), (int) enterInt.getLocation().getY() + 500);
			// makes sure the user enters an integer and not a string
			do {
				integerString = JOptionPane.showInputDialog(
						"Look up the Page Rank Scores of the link by entering the corresponding number of the link");
				if (integerString == null) {
					break;
				} else if (!integerString.matches("^[0-9]*$")) {
					enterInt.setVisible(true);

				}

			} while (!integerString.matches("^[0-9]*$") || integerString.equals(""));
			// if the user decides to cancel, then the windows of the links disappears
			if (integerString == null) {
				scoreWindow.setVisible(false);
			} else {
				// the int is tested if it's between 1 and 30, otherwise the user is given an
				// error message
				enterInt.setVisible(false);
				int number = Integer.parseInt(integerString);
				if (number > 30 || number < 1) {
					enterInt.setVisible(true);
					scoreWindow.setVisible(false);
				} else {
					// if the int is between 1 and 30, a window is added which shows the 4 factor
					// scores of the link
					scoreWindow.setVisible(false);
					JFrame viewWindow = new JFrame("View Score");
					viewWindow.setVisible(true);
					viewWindow.setSize(500, 500);
					viewWindow.setLayout(new GridLayout(7, 1));
					viewWindow.add(new JLabel("URL:  " + linkList[number - 1].getUrl(), JLabel.CENTER));
					viewWindow.add(new Label("The frequency and location of keywords within the web page: "
							+ linkList[number - 1].getfreqNum()));
					viewWindow
							.add(new Label("How long the web page has existed: " + linkList[number - 1].getexistNum()));
					viewWindow.add(new Label("The number of other web pages that link to the page in question: "
							+ linkList[number - 1].getlinkNum()));
					viewWindow.add(new Label("How much the webpage owner has paid to Google for advertisement purpose: "
							+ linkList[number - 1].getadverNum()));
				}
			}
		}
		// if the user clicks to view the links in order
		if (event.equals("viewRank")) {
			Url[] copy = linkList;
			HeapEditor linkSorter = new HeapEditor();
			linkSorter.heapSorter(copy); // the link list is sorted from least to greatest using heap sorter
			JFrame rankWindow = new JFrame("View Links Based on Rank");
			rankWindow.setVisible(true);
			rankWindow.setSize(500, 500);
			rankWindow.setLayout(new GridLayout(31, 1));
			rankWindow.add(new JLabel("The ranked 30 links are as followed:", JLabel.CENTER));
			int j = 1;
			for (int i = 29; i >= 0; i--) { // the link list is then printed backwards since it's from least to greatest
				rankWindow.add(new Label((j + ". " + copy[i].getUrl() + ",   Page Rank:  " + copy[i].getTotal())));
				j++;
			}
		}
		// if the user wants to view and extract the highest link in queue
		if (event.equals("viewTop")) {
			JFrame error = new JFrame();
			error.setSize(500, 250);
			JLabel errorMessage = new JLabel("No items in queue. Insert a link or try a New Search", JLabel.CENTER);
			error.add(errorMessage);
			JFrame success = new JFrame("Success");
			success.setSize(500, 250);
			success.setLayout(new GridLayout(2, 1));

			try { // creates a success window and shows the top link for the user
				Url topLink = heapQueue.heapExtractMax(linkQueue);
				success.add(new JLabel("The URL is " + topLink.getUrl(), JLabel.CENTER));
				success.add(new JLabel("It has been removed from the Queue", JLabel.CENTER));
				success.setVisible(true);
			} catch (Exception e) { // if there is no item within the link, then an error message is given.
				error.setVisible(true);
			}

		}
		// if the user presses the button to increase a specific link
		if (event.equals("increaseKey")) {
			// a window is created and shown to the user to show the different links within
			// the queue
			JFrame rankWindow = new JFrame("View Links Within the Queue");
			rankWindow.setVisible(true);
			rankWindow.setSize(500, 500);
			rankWindow.setLayout(new GridLayout(31, 1));
			rankWindow.add(new JLabel("These are the links within the Queue:", JLabel.CENTER));
			int j = 1;
			for (int i = 0; i < heapQueue.heapsize; i++) {
				rankWindow.add(
						new Label((j + ". " + linkQueue[i].getUrl() + ",   Page Rank:  " + linkQueue[i].getTotal())));
				j++;
			}
			String integerString;
			String rankString;
			JFrame enterInt = new JFrame();
			enterInt.setSize(300, 300);
			JLabel noSearch = new JLabel("Enter a valid integer. Try again.", JLabel.CENTER);
			enterInt.add(noSearch);
			enterInt.setLocation((int) enterInt.getLocation().getX(), (int) enterInt.getLocation().getY() + 500);
			JFrame enterCorrectInt = new JFrame();
			enterCorrectInt.setSize(750, 250);
			JLabel enterCorrectNum = new JLabel(
					"Enter an integer less than or equal to 400, and greater than the orginal Page Rank. Try Again.",
					JLabel.CENTER);
			enterCorrectInt.add(enterCorrectNum);
			enterCorrectInt.setLocation((int) enterCorrectInt.getLocation().getX(),
					(int) enterCorrectInt.getLocation().getY() + 500);
			// the user is then asked to choose a link to increase the Page Rank by entering
			// the corresponding number of the link
			// if the number is not an int, then an error message is given
			do {
				integerString = JOptionPane.showInputDialog(
						"Choose a Link to increase the Page Rank by entering the corresponding number");
				if (integerString == null) {
					break;
				} else if (!integerString.matches("^[0-9]*$")) {
					enterInt.setVisible(true);

				}

			} while (!integerString.matches("^[0-9]*$") || integerString.equals(""));
			// if the user decides to close, then the rankWindow disappears
			if (integerString == null) {
				rankWindow.setVisible(false);
			} else {
				// makes sure the user enter a number that is in the heapsize range (1 to
				// heapsize max)
				if (Integer.parseInt(integerString) > heapQueue.heapsize || Integer.parseInt(integerString) <= 0) {
					rankWindow.setVisible(false);
					enterInt.setVisible(true);

				} else {
					enterInt.setVisible(false);
					int index = Integer.parseInt(integerString) - 1;
					// ask the user how much they would like the Page Rank to be
					do {
						rankString = JOptionPane.showInputDialog("Enter how much you would like the PageRank to be ");
						if (rankString == null) {
							break;
						} else if (!rankString.matches("^[0-9]*$")) {
							enterInt.setVisible(true);

						}

					} while (!rankString.matches("^[0-9]*$") || rankString.equals("")); // make sure the entered value
																						// is not a string
					if (rankString == null) { // if the user decides to cancel, the rank window closes
						rankWindow.setVisible(false);
					} else {

						enterInt.setVisible(false);
						int key = Integer.parseInt(rankString);
						if (key > 400) { // checks if the key is greater than 400, and returns an error if it is
							enterCorrectInt.setVisible(true);
							rankWindow.setVisible(false);
						} else {

							try {
								heapQueue.heapIncreaseKey(linkQueue, index, key); // the heap increase key is called to
																					// increase the key of the URL and
																					// rearrange its place
								rankWindow.setVisible(false);
								JFrame sucessWindow = new JFrame("Sucess");
								sucessWindow.setVisible(true);
								sucessWindow.setSize(250, 250);
								sucessWindow.add(new JLabel("Sucess! The Queue has been updated!", JLabel.CENTER));

							} catch (Exception e) { // if the number is less than the RankScore, then an error message
													// is given
								enterCorrectInt.setVisible(true);
								rankWindow.setVisible(false);
							}
						}
					}
				}
			}

		}
		// if the user wants to add a url
		if (event.equals("insert")) {
			if (heapQueue.heapsize == 30) { // gives error message if the heap is at max capacity of 30
				JFrame error = new JFrame("Error");
				error.add(new JLabel("Queue is full.", JLabel.CENTER));
				error.setSize(300, 300);
				error.setVisible(true);
			} else {

				JFrame error = new JFrame("Error");
				error.add(new JLabel("Error. Link is empty "), JLabel.CENTER);
				error.setSize(500, 250);

				String url = JOptionPane.showInputDialog("Enter a the link of the url"); // asks user for the URL
				if (url == null) {
					// nothing happens
				} else if (url.equals("")) {
					error.setVisible(true); // error message if URL is empty
				} else {
					JFrame rankError = new JFrame("Error");
					rankError.add(new JLabel("Error. PageRank must be an integer equal or less than 400. Try Again",
							JLabel.CENTER));
					rankError.setSize(500, 250);
					rankError.setLocation((int) rankError.getLocation().getX(),
							(int) rankError.getLocation().getY() + 500);
					String pageRank;
					do { // asks user for the page rank of the link they would like to add
						pageRank = JOptionPane.showInputDialog("Enter the Page Rank of the link");
						if (pageRank == null) {
							break;
						} else if (!pageRank.matches("^[0-9]*$")) { // makes sure the input value is not a string
							rankError.setVisible(true);

						}

					} while (!pageRank.matches("^[0-9]*$") || pageRank.equals(""));
					if (pageRank == null) {
						rankError.setVisible(false);
					} else {
						int intPageRank = Integer.parseInt(pageRank);
						rankError.setVisible(false);
						if (intPageRank > 400 || intPageRank < 0) { // makes sure the int value is not negative or
																	// larger than 400
							rankError.setVisible(true);

						} else {
							try {
								heapQueue.heapInsert(linkQueue, intPageRank, url); // otherwise heap insert is called to
																					// put the link into queue
								JFrame success = new JFrame("Sucess");
								success.setSize(500, 150);
								success.add(
										new JLabel("Success, the link has been added to the Queue!", JLabel.CENTER));
								success.setVisible(true);
							} catch (Exception e) {
								// do nothing as page rank will definitely be larger than 0
							}
						}
					}
				}
			}
		}
		// if the user clicks on the exit window button, the application closes
		if (event.equals("exit")) {
			System.exit(0);
		}
		// if the user clicks on the button to view the links within the queue
		if (event.equals("viewQueue")) {
			JFrame rankWindow = new JFrame("View Links Within the Queue");
			rankWindow.setVisible(true);
			rankWindow.setSize(500, 500);
			rankWindow.setLayout(new GridLayout(31, 1));
			rankWindow.add(new JLabel("These are the links within the Queue:", JLabel.CENTER));
			int j = 1;
			for (int i = 0; i < heapQueue.heapsize; i++) {
				rankWindow.add(
						new Label((j + ". " + linkQueue[i].getUrl() + ",   Page Rank:  " + linkQueue[i].getTotal())));
				j++;
			}
			rankWindow.add(new JLabel(""));
			if (heapQueue.heapsize > 0) { // shows the first link in queue
				rankWindow.add(new JLabel("The first link in queue is:  " + heapQueue.heapMaximum(linkQueue).getUrl()));
			} else {
				rankWindow.add(new JLabel("No links in queue", JLabel.CENTER)); // if there are no links in queue, the
																				// program will show that there is no
																				// link in queue
			}

		}
	}

	/**
	 * main method which will essentially create the Google SimulationF
	 * 
	 */
	public static void main(String args[]) {
		GoogleSimulation searcher = new GoogleSimulation();

	}

}
