package pa01;

/**
 * This class allows one to create a URL object to store the information about
 * the URL
 * 
 *
 */
public class Url {
	public String url;
	public int freqNum;
	public int existNum;
	public int linkNum;
	public int adverNum;
	public int pageRank;

	/***
	 * Constructor which creates a URL and calculates the page Rank
	 * 
	 * @param url
	 *            - the link of the url
	 * @param freqNum
	 *            - the frequency factor of the Page Rank
	 * @param existNum
	 *            - the existence factor of the Page Rank
	 * @param linkNum
	 *            - the link factor of the Page Rank
	 * @param adverNum
	 *            - the advertisement factor the Page Rank
	 */
	public Url(String url, int freqNum, int existNum, int linkNum, int adverNum) {
		this.url = url;
		this.freqNum = freqNum;
		this.existNum = existNum;
		this.linkNum = linkNum;
		this.adverNum = adverNum;
		pageRank = freqNum + existNum + linkNum + adverNum;
	}

	/**
	 * getter method which returns the page Rank
	 * 
	 * @return - the page rank
	 */
	public int getTotal() {
		return pageRank;
	}

	/**
	 * getter method which returns the url of the link
	 * 
	 * @return - the url of the link
	 */
	public String getUrl() {
		return url;
	}

	/***
	 * getter method which returns the frequency factor of the link
	 * 
	 * @return - the frequency factor number
	 */
	public int getfreqNum() {
		return freqNum;
	}

	/***
	 * getter method which returns the existence factor of the link
	 * 
	 * @return the existence factor numbers
	 */
	public int getexistNum() {
		return existNum;
	}

	/***
	 * getter method which returns the link factor of the link
	 * 
	 * @return - the link factor number of the URl
	 */
	public int getlinkNum() {
		return linkNum;
	}

	/***
	 * getter method which returns the advertisement factor the URL
	 * 
	 * @return - the advertisment factor of the URL
	 */
	public int getadverNum() {
		return adverNum;
	}

	/***
	 * setter method which set the page Rank of the URL
	 * 
	 * @param pageRank
	 *            - the new page Rank
	 */
	public void setPageRank(int pageRank) {
		this.pageRank = pageRank;
	}

}
