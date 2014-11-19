
import java.io.*;
import java.util.*;

public class DSmeritlist
{
	public ArrayList<Candidate> candidates;
	public ArrayList<Candidate> DSlist;
	public int Count;
	public int totalCount;
	public DSmeritlist()
	{
		this.totalCount=0;
		this.Count=0;
		this.candidates=new ArrayList<Candidate>();
	}	
	public void insert(Candidate c)
	{
		this.totalCount=this.totalCount+1;
			if(c.rank[0]!=0){
				//code
				switch(Count){
					case 0:
						candidates.add(0,c);
						break;
					case 1:
						if(c.rank[0]>=candidates.get(0).rank[1])
						{
							candidates.add(1,c);
						}
						else
						{
							candidates.add(0,c);
						}			
						break;
					default:
						if(candidates.get(0).rank[0]>=c.rank[0]){candidates.add(0,c);break;}
						for(int j=1;j<Count;j++)
						{
							if(c.rank[0]>=candidates.get(j-1).rank[0])
							{
								if(c.rank[0]<=candidates.get(j).rank[0]){
									candidates.add(j,c);
									break;
								}
							}
						}
						if(candidates.get(Count-1).rank[0]<=c.rank[0])candidates.add(Count,c);
						break;
				}
			Count=Count+1;
		}
	}
}
