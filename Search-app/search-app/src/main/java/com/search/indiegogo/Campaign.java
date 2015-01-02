package com.search.indiegogo;

import java.net.URL;

/**
 * 
 * A Campaign consists of a title, a tagline, a hyperlink and a link to its photo.
 * A minimal representation of Indiegogo's campaign
 * 
 * @author sethuraman
 *
 */
public class Campaign 
{
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tagline == null) ? 0 : tagline.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campaign other = (Campaign) obj;
		if (tagline == null) {
			if (other.tagline != null)
				return false;
		} else if (!tagline.equals(other.tagline))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public Campaign(String title, String tagline, URL photourl, String hyperLink)
	{
		this.tagline = tagline;
		this.title = title;
		this.hyperlink = "https://www.indiegogo.com/projects/" + hyperLink;
		this.photourl = photourl;	
	}
	
	public String getTitle() 
	{
		return title;
	}
	
	public String getTagline() 
	{
		return tagline;
	}	
	
	public String getHyperlink() 
	{
		return hyperlink;
	}
	
	public URL getPhotourl() 
	{
		return photourl;
	}

	private final String title, tagline;
	private final URL photourl;
	private final String hyperlink;
	
	@Override
	public String toString() 
	{
		return title + "\n" + tagline + "\nlink : " + "Campaign link : " 
	                 + hyperlink + "\nPhotograph-medium :" + photourl.toString();
	}
}
