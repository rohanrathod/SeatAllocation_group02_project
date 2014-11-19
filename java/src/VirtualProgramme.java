
import java.io.*;
import java.util.*;
/**
 * this code creates the class for virtualProgramme
 * <h1>Virtual Programme</h1>
 * A virtual programme is a sub programme created in order to disintegerate a programme into corresponding sections
 * while using the criterion of the input provided in this case category of the students.
 */
//*****************************************************************************************************

///////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////

//*****************************************************************************************************
/**
 * it is the class which stores the program's variables say for eg.the no of seats available etc.
 */
public class VirtualProgramme
{
	
	public String Code;
	public String program;
	public float closingRank;
	public int category;
	public int PD_status;
	public int quota;
	public ArrayList<Candidate> waitlist;
	public ArrayList<Candidate> foreign_waitlist;
	public int seats_available;
	public ArrayList<Candidate> DS_waitlist;
	/**
	 * this is the constructor 
	 * it creates a virtual program that has the parameter as provided
	 */
	public VirtualProgramme(String codecourse,String name,int cat, String pd, int q)
	{
		this.Code=codecourse;
		this.program=name;
		this.category=cat;
		if(pd=="Y")this.PD_status=1;
		else this.PD_status=0;
		this.quota=q;
		this.waitlist=new ArrayList<Candidate>();
		this.foreign_waitlist=new ArrayList<Candidate>();
		this.seats_available=q;
	}
	/**
	 * this is an important function for the merit list allocation concept
	 * it returns whether a candidate shall get a programme or not.
	 */
	
	public boolean result(Candidate c)
	{
		
		if(this.seats_available!=0){
			for(int i=0;i<4;i++){
				if(category==i){
					if(this.PD_status==1){
						if(c.rank[i+4]!=0){
							if(c.Foreign==0){
								if(this.seats_available==0)closingRank=c.rank[i+4];
								this.seats_available-=1;
							}
							return true;
						}
					}
					else{
						if(c.rank[i]!=0){
							if(c.Foreign==0){
								if(this.seats_available==0)closingRank=c.rank[i];
								this.seats_available-=1;
							}
						}
					}
				}
			}
			return true;
		}
		else {
			for(int i=0;i<4;i++){
				if(category==i){
					if(this.PD_status==1){
						if(c.rank[i+4]==closingRank){return true;}
					}
					else{
						if(c.rank[i]==closingRank){return true;}
					}
				}
			}
			return false;
		}
	}
	/**
	 * this function is used to insert an element in the list where it may be furthur processed
	 */
	public void addInWaitlist(Candidate c){
		this.waitlist.add(c);
	}
	/**
	 * this function sorts the list of the candidates
	 * based on the rank and category,the lists are sorted
	 */
	public void sort(){
		if(this.category==0){Collections.sort(this.waitlist,new ComparatorGen());}
		if(this.category==1){Collections.sort(this.waitlist,new ComparatorObc());}
		if(this.category==2){Collections.sort(this.waitlist,new ComparatorSc());}
		if(this.category==3){Collections.sort(this.waitlist,new ComparatorSt());}
		if(this.category==4){Collections.sort(this.waitlist,new ComparatorGenpd());}
		if(this.category==5){Collections.sort(this.waitlist,new ComparatorObcpd());}
		if(this.category==6){Collections.sort(this.waitlist,new ComparatorScpd());}
		if(this.category==7){Collections.sort(this.waitlist,new ComparatorStpd());}
	}
	/**
	 * this function is used only in gale Shapely Algorithm
	 * this program filters the top qualified candidates and allots them in the waitlist
	 * it also returns the candidates not selected
	 */
	public ArrayList<Candidate> filter(){
		if(this.waitlist.size()>this.seats_available){
			//heres a problm.fix it.
			ArrayList<Candidate> c=new ArrayList<Candidate>(this.waitlist.subList(this.seats_available,this.waitlist.size()));
			this.waitlist=new ArrayList<Candidate>(this.waitlist.subList(0,this.seats_available));
			return c;
			//ArrayList<Candidate> original=new ArrayList<Candidate>(this.waitlist.subList(0,this.waitlist.size()));
		}
		else{
			return new ArrayList<Candidate>();
		}
	}
}
