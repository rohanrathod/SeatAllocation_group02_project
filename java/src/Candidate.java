
import java.io.*;
import java.util.*;
/**
 * 
 * @author Team Strikers
 * @version 1.0
 * <h1> Seat Allocation Project</h1>
 * this projrect is for allocation of seats to candidates using galeShapely algorithm and using the concept of merit list allocation 
 */
/**
 * this class is used to store the parameters of a candidate appling for the programs.
 * It stores the important features and apply operation on them 
 */
public class Candidate {

	public String studentID;
	public String category;
	public int PD;
	public int DS;
	public int Foreign;
	public String allocated_programme;//allocated programme
	public int indexOfAllocatedProgramme;
	public ArrayList<String> pref_list;
	public ArrayList<String> pref_list1;	
	public int countOfPref;
	public int indexOfAppliedProgs;//index in pref_list upto which candidate has applied
	public String next_preference;
	public float[] rank;
	public int DS_count;

	/**
	 * this is the default constructor.
	 * creates a template element. 
	 */
	public Candidate()
	{
		this.DS_count=0;
		this.rank=new float[8];
		this.allocated_programme="-1";
		this.indexOfAppliedProgs=-1;
		this.pref_list=new ArrayList<String>();
		this.pref_list=new ArrayList<String>();
		for(int i=0;i<8;i++){
			this.rank[i]=0;
		}
		this.countOfPref=0;
	}
	/**
	 * this is a detailed constructor.
	 * it is a detailed method that creates an object with all the specified values. 
	 * used mostly in the meritListAllocation concept
	 */
	public Candidate(String Id,String cat, String PDstatus,float ge,float obc,float sc,float st,float gepd,float obcpd,float scpd,float stpd)
	{
		this.DS_count=0;
		this.rank=new float[8];
		this.indexOfAppliedProgs=-1;
		this.studentID=Id;
		this.category=cat;
		if(PDstatus=="Y")this.PD=1;
		else this.PD=0;
		if(cat=="DS")this.DS=1;
		else this.DS=0;
		if(cat=="F")this.Foreign=1;
		else this.Foreign=0;
		this.allocated_programme="-1";
		this.pref_list=new ArrayList<String>();
		this.pref_list1=new ArrayList<String>();
		this.countOfPref=0;
		this.indexOfAllocatedProgramme=-1;
		this.rank[0]=ge;
		this.rank[1]=obc;
		this.rank[2]=st;
		this.rank[3]=sc;
		this.rank[4]=gepd;
		this.rank[5]=obcpd;
		this.rank[6]=stpd;
		this.rank[7]=scpd;
	}
	/**
	 * this is a brief yet complete constructor
	 * it is used mostly in the galeShapely Algorithm 
	 */
	public Candidate(String Id,String cat, String PDstatus,float ge,float catrank,float catpd)
	{
		this.DS_count=0;
		this.rank=new float[8];
		this.studentID=Id;
		this.category=cat;
		this.indexOfAppliedProgs=-1;
		if(PDstatus=="Y")this.PD=1;
		else this.PD=0;
		if(cat=="DS")this.DS=1;
		else this.DS=0;
		if(cat=="F")this.Foreign=1;
		else this.Foreign=0;
		this.allocated_programme="-1";
		this.pref_list=new ArrayList<String>();
		this.pref_list1=new ArrayList<String>();
		this.countOfPref=0;
		this.indexOfAllocatedProgramme=-1;
		for(int i=0;i<8;i++)this.rank[i]=0;
		switch(cat){
		case "GE":this.rank[0]=catrank;this.rank[4]=catpd;
		case "OBC":this.rank[1]=catrank;this.rank[5]=catpd;
		case "SC":this.rank[2]=catrank;this.rank[6]=catpd;
		case "ST":this.rank[3]=catrank;this.rank[7]=catpd;
		}
	}
///////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * this method inserts all the preference of the candidate 
	 * it stores the preferences and the no.of preferences inserted by the candidate in the class
	 */
	public void add_pref(String str)
	{
		
		int j=0;
		String s=new String();
		for(int i=0;i<str.length();i++)
		{
			if(str.charAt(i)=='_')
			{
				s=str.substring(j,i);
				this.pref_list.add(s);
				j=i+1;
				this.countOfPref=this.countOfPref+1;
			}
			if(i==str.length()-1)
			{
				s=str.substring(j,i+1);
				this.pref_list.add(s);
				this.countOfPref=this.countOfPref+1;
			}
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * this function runs the process of traversing through the preferences of the candidate.
	 * it sets the preference to the next one
	 * it returns whether a new preference is stored or not. 
	 */
	public boolean set_next_preference()
	{
		
		if(this.allocated_programme.equals("-1")&&this.indexOfAppliedProgs<this.pref_list1.size()-1)
		{
			this.next_preference=this.pref_list1.get(this.indexOfAppliedProgs+1);
			this.indexOfAppliedProgs+=1;
			return true;
		}
		else
		{
			return false;
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * this function just add the virtual programme preference wise in the list
	 */
	public void addVirtualPref()
	{
		this.pref_list1=new ArrayList<String>();
		String s;
		for(int i=0;i<this.pref_list.size();i++){
			s=this.pref_list.get(i);
			if(this.category.equals("GE"))
			{
				if(this.PD==1)
				{
					this.pref_list1.add(s.concat("0"));//GE
					this.pref_list1.add(s.concat("1"));//ge
					this.pref_list1.add(s.concat("4"));//GE pd
					this.pref_list1.add(s.concat("5"));
				}
				else{
					this.pref_list1.add(s.concat("0"));//ge
					this.pref_list1.add(s.concat("4"));
					this.pref_list1.add(s.concat("1"));//obc
					this.pref_list1.add(s.concat("5"));
				}
			}
			else if(this.category.equals("OBC"))
			{
				if(this.PD==1)
				{
					this.pref_list1.add(s.concat("0"));//gen
					this.pref_list1.add(s.concat("4"));//gepd
					this.pref_list1.add(s.concat("1"));//obc
					this.pref_list1.add(s.concat("5"));//obc pd
				}
				else 
				{
					this.pref_list1.add(s.concat("0"));//gen
					this.pref_list1.add(s.concat("1"));//obc
					this.pref_list1.add(s.concat("4"));
					this.pref_list1.add(s.concat("5"));
				}	
			}
			else if(this.category.equals("SC"))
			{
				if(this.PD==1)
				{
					this.pref_list1.add(s.concat("0"));//gen
					this.pref_list1.add(s.concat("2"));//sc
					this.pref_list1.add(s.concat("4"));//gen pd
					this.pref_list1.add(s.concat("6"));//sc pd
					this.pref_list1.add(s.concat("1"));
					this.pref_list1.add(s.concat("5"));
				}
				else 
				{
					this.pref_list1.add(s.concat("0"));//gen
					this.pref_list1.add(s.concat("2"));//sc
					this.pref_list1.add(s.concat("1"));
					this.pref_list1.add(s.concat("4"));
					this.pref_list1.add(s.concat("5"));
					this.pref_list1.add(s.concat("6"));
				}	
			}
			else if(this.category.equals("ST"))
			{
				if(this.PD==1)
				{
					this.pref_list1.add(s.concat("0"));//gen
					this.pref_list1.add(s.concat("3"));//st
					this.pref_list1.add(s.concat("4"));//gen-pd
					this.pref_list1.add(s.concat("7"));//st-pd
					this.pref_list1.add(s.concat("1"));
					this.pref_list1.add(s.concat("5"));
				}
				else 
				{
					this.pref_list1.add(s.concat("0"));//gen
					this.pref_list1.add(s.concat("3"));//st
					this.pref_list1.add(s.concat("1"));
					this.pref_list1.add(s.concat("4"));
					this.pref_list1.add(s.concat("5"));
					this.pref_list1.add(s.concat("7"));
				}	
			}
			else if(this.category.equals("DS"))
			{
				if(this.PD==1)
				{
					this.pref_list1.add(s.concat("0"));//GE
					this.pref_list1.add(s.concat("1"));//ge
					this.pref_list1.add(s.concat("4"));//GE pd
					this.pref_list1.add(s.concat("5"));
				}
				else{
					this.pref_list1.add(s.concat("0"));//ge
					this.pref_list1.add(s.concat("4"));
					this.pref_list1.add(s.concat("1"));//obc
					this.pref_list1.add(s.concat("5"));
				}

			}
			else if(this.category.equals("F"))
			{
				if(this.PD==1)
				{
					this.pref_list1.add(s.concat("0"));//GE
					this.pref_list1.add(s.concat("1"));//ge
					this.pref_list1.add(s.concat("4"));//GE pd
					this.pref_list1.add(s.concat("5"));
				}
				else{
					this.pref_list1.add(s.concat("0"));//ge
					this.pref_list1.add(s.concat("4"));
					this.pref_list1.add(s.concat("1"));//obc
					this.pref_list1.add(s.concat("5"));
				}
			}
		}
	}
}