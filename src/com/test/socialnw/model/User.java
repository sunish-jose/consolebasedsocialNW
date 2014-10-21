package com.test.socialnw.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class User {
	
	private String name;
	
	private TimeLine timeline;
	
	public User(String name) {
		this.name=name;
	}
	
	private Set<User> followes;
	
	public Set<User> getFollowes() {
		return followes==null?Collections.<User>emptySet():followes;
	}
	
	public void addFollowers(User user) {
		if(followes==null) {
			followes=new HashSet<User>();
		}
		if(user!=null) {
			followes.add(user);
		}
	}

	protected void setFollowes(Set<User> followes) {
		this.followes = followes;
	}

	public TimeLine getTimeline() {
		return timeline;
	}

	public void setTimeline(TimeLine timeline) {
		this.timeline = timeline;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
}
