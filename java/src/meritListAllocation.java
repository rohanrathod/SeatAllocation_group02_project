
import java.io.*;
import java.util.*;
/**
 * @version 1.0
 * @author Team Strikers
 * this program allots programme to candidates based on the concepts of meritListAllocation
 */

/**
 * this class stores the input candidates and the programmes available
 * it allots the programmes to the candidates using the concept of merit list allocation
 */
public class meritListAllocation {
	public int candidateCount,programCount;
	public ArrayList<Candidate> allcandidate;
	public HashMap<String,VirtualProgramme> allprogram;
	public MeritList merit;
	public DSmeritlist DSmerit;
	public HashMap<String,Integer> institutes;
	public meritListAllocation()
	/**
	 * this is the basic constructor which creates an allocation corresponding to an input
	 */
	{
		
		this.DSmerit=new DSmeritlist();
		this.institutes=new HashMap<String,Integer>();
		this.candidateCount=0;
		this.programCount=0;
    		this.allprogram=new HashMap<String,VirtualProgramme>();
	    	this.allcandidate=new ArrayList<Candidate>();
	    	this.merit=new MeritList();
		  String csvFileToRead = "programs.csv";  
		  BufferedReader br = null;  
		  String line = "",line1;  
		  String splitBy = ",";  
		  /**
		    * First of all we read the programmes data and store them in the corresponding places
		    */
		  try{
		   br = new BufferedReader(new FileReader(csvFileToRead));
		   line=br.readLine();
		   
		   while ((line = br.readLine()) != null) {  
			String[] input = line.split(splitBy);
			allprogram.put(input[1].concat("0"),new VirtualProgramme(input[1],input[2],0,"N",Integer.parseInt(input[3])));
			allprogram.put(input[1].concat("1"),new VirtualProgramme(input[1],input[2],1,"N",Integer.parseInt(input[4])));
			allprogram.put(input[1].concat("2"),new VirtualProgramme(input[1],input[2],2,"N",Integer.parseInt(input[5])));
			allprogram.put(input[1].concat("3"),new VirtualProgramme(input[1],input[2],3,"N",Integer.parseInt(input[6])));
			allprogram.put(input[1].concat("4"),new VirtualProgramme(input[1],input[2],0,"Y",Integer.parseInt(input[7])));
			allprogram.put(input[1].concat("5"),new VirtualProgramme(input[1],input[2],1,"Y",Integer.parseInt(input[8])));
			allprogram.put(input[1].concat("6"),new VirtualProgramme(input[1],input[2],2,"Y",Integer.parseInt(input[9])));
			allprogram.put(input[1].concat("7"),new VirtualProgramme(input[1],input[2],3,"Y",Integer.parseInt(input[10])));
		   this.programCount=this.programCount+8;
		   this.institutes.put(input[1].substring(0,1),0);
		   }
		  } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		   csvFileToRead="choices.csv";
		   BufferedReader cr = null; 
		   String csvFileToRead1="ranklist.csv";
		   HashMap<String,StringTriple> s = new HashMap<String,StringTriple>();
		   /////////////////////////////////////////////////////////////

		   /**
		    * now we read the choices filled by the candidatees and store them in order to use them later for allocation
		    */
		   try{
			   br = new BufferedReader(new FileReader(csvFileToRead));
			   line=br.readLine();
			   while ((line = br.readLine()) != null) {  
					String[] input = line.split(splitBy);
					StringTriple s1=new StringTriple();
					for(int i=0;i<3;i++){
						s1.a[i]=input[i+1];
					}
					s.put(input[0],s1);
				}
		   }
		   catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		   catch (IOException e) {
				e.printStackTrace();
			}
		   finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			/////////////////////////////////////////////////////////////
		   /**
		    * now we read the ranks of the candidates and instantiate the candidates
		    * it is this time only when we seperate the DS category students.
		    */
		   try{
			   cr = new BufferedReader(new FileReader(csvFileToRead1));
			   line1=cr.readLine();
			   
			   while((line1 = cr.readLine()) != null){
					String[] input1 = line1.split(splitBy);
					Candidate c1=new Candidate(input1[0],s.get(input1[0]).a[0],s.get(input1[0]).a[1],Float.parseFloat(input1[3]),Float.parseFloat(input1[4]),Float.parseFloat(input1[5]),Float.parseFloat(input1[6]),Float.parseFloat(input1[7]),Float.parseFloat(input1[8]),Float.parseFloat(input1[9]),Float.parseFloat(input1[10]));
					c1.add_pref(s.get(input1[0]).a[2]);
					allcandidate.add(c1);
					if(s.get(input1[0]).a[0].equals("DS")){
						this.DSmerit.insert(c1);
					}
					else{
						merit.insert(c1);
						this.candidateCount=this.candidateCount+1;
					}
			   }
			}
			/*
			  			switch(institutes.get(s.get(input1[0]).a[2].charAt(1))){
							case 0:
								c1.allocated_programme=c1.pref_list.get(0);
								this.institutes.put(s.get(input1[0]).a[2].substring(0,1),1);
								this.DS.add(c1);
								ds=true;break;
							case 1:
								c1.allocated_programme=c1.pref_list.get(0);
								this.institutes.put(s.get(input1[0]).a[2].substring(0,1),2);
								this.DS.add(c1);
								ds=true;break;
							case 2:break;
			*/
		   catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		   catch (IOException e) {
				e.printStackTrace();
			}
		   finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			/////////////////////////////////////////////////////////
			
			boolean bol=false;
			/**
			 * firstly we assign the DS candidates what they deserves on the basis of there ranks.
			 * at this point,the DS candidates are worked on.
			 */
			for(int i=0;i<DSmerit.candidates.size();i++){
				//implementation here
				for(int j=0;j<DSmerit.candidates.get(i).pref_list.size();i++){
					if(this.institutes.get(DSmerit.candidates.get(i).pref_list.get(j).charAt(0))==null){
						bol=true;
						DSmerit.candidates.get(i).countOfPref=j+1;
						DSmerit.candidates.get(i).allocated_programme=DSmerit.candidates.get(i).pref_list.get(j);
						merit.insert(DSmerit.candidates.get(i));
						this.institutes.put(DSmerit.candidates.get(i).pref_list.get(0).substring(0,1),1);
						break;
					}
					if(this.institutes.get(DSmerit.candidates.get(i).pref_list.get(0).charAt(0))==1){
						bol=true;
						DSmerit.candidates.get(i).countOfPref=j+1;
						DSmerit.candidates.get(i).allocated_programme=DSmerit.candidates.get(i).pref_list.get(j);
						merit.insert(DSmerit.candidates.get(i));
						this.institutes.put(DSmerit.candidates.get(i).pref_list.get(0).substring(0,1),2);
						break;
					}
				}
				if(!bol)merit.insert(DSmerit.candidates.get(i));
			}
			/*						switch(institutes.get(s.get(input1[0]).a[2].charAt(1))){
							case 0:
								c1.allocated_programme=c1.pref_list.get(0);
								this.institutes.put(s.get(input1[0]).a[2].substring(0,1),1);
								this.DS.add(c1);
								ds=true;break;
							case 1:
								c1.allocated_programme=c1.pref_list.get(0);
								this.institutes.put(s.get(input1[0]).a[2].substring(0,1),2);
								this.DS.add(c1);
								ds=true;break;
							case 2:break;*/
			/////////////////////////////////////////////////////////
		   int c=0;
		   /**
		    * now we run the processing of the algorithm
		    */
		   for(int i1=0;i1<8;i1++){
			   for(int j1=c+0;j1<c+merit.Count[i1];j1++){
				   for(int k1=0;k1<merit.candidates.get(j1).countOfPref;k1++){
					   if(allprogram.get(merit.candidates.get(j1).pref_list.get(k1).concat(String.valueOf(i1))).result(merit.candidates.get(j1)))
					   {
							merit.candidates.get(j1).indexOfAllocatedProgramme=k1+1;
							merit.candidates.get(j1).allocated_programme=allprogram.get(merit.candidates.get(j1).pref_list.get(k1).concat(String.valueOf(i1))).Code;
							merit.candidates.get(j1).countOfPref=merit.candidates.get(j1).indexOfAllocatedProgramme;
					   }
				   }
			   }
			   c=c+merit.Count[i1];
		   }
		   /**
		    * now we dereserve the unallocated seats.
		    * and furthur reallocate the seats.
		    */
		   /////////////////////////////////////////////////////////////
		   for (String key : this.allprogram.keySet()) {
			    // ...
			   if(key.charAt(key.length()-1)=='1'){
				   if(this.allprogram.get(key).seats_available!=0){
					   this.allprogram.get((key.substring(0,key.length()-1)).concat("0")).seats_available+=this.allprogram.get(key).seats_available;
					   this.allprogram.get(key).seats_available=0;
				   }
			   }
			   if(key.charAt(key.length()-1)=='4'){
				   if(this.allprogram.get(key).seats_available!=0){
					   this.allprogram.get((key.substring(0,key.length()-1)).concat("0")).seats_available+=this.allprogram.get(key).seats_available;
					   this.allprogram.get(key).seats_available=0;
				   }
			   }
			   if(key.charAt(key.length()-1)=='5'){
				   if(this.allprogram.get(key).seats_available!=0){
					   this.allprogram.get((key.substring(0,key.length()-1)).concat("0")).seats_available+=this.allprogram.get(key).seats_available;
					   this.allprogram.get(key).seats_available=0;
				   }
			   }
			   if(key.charAt(key.length()-1)=='6'){
				   if(this.allprogram.get(key).seats_available!=0){
					   this.allprogram.get((key.substring(0,key.length()-1)).concat("2")).seats_available+=this.allprogram.get(key).seats_available;
					   this.allprogram.get(key).seats_available=0;
				   }
			   }
			   if(key.charAt(key.length()-1)=='7'){
				   if(this.allprogram.get(key).seats_available!=0){
					   this.allprogram.get((key.substring(0,key.length()-1)).concat("3")).seats_available+=this.allprogram.get(key).seats_available;
					   this.allprogram.get(key).seats_available=0;
				   }
			   }
			   ///////////////////////////////////////////////////////////////////////////////
			   c=0;
			   for(int i1=0;i1<8;i1++){
				   if(i1==0||i1==2||i1==3){
					   for(int j1=c+0;j1<c+merit.Count[i1];j1++){
						   for(int k1=0;k1<merit.candidates.get(j1).countOfPref-1;k1++){
							   if(allprogram.get(merit.candidates.get(j1).pref_list.get(k1).concat(String.valueOf(i1))).result(merit.candidates.get(j1)))
							   {
								   if(merit.candidates.get(j1).indexOfAllocatedProgramme!=-1){allprogram.get((merit.candidates.get(j1).pref_list.get(merit.candidates.get(j1).indexOfAllocatedProgramme)).concat(i1+"0")).seats_available+=1;}
									merit.candidates.get(j1).indexOfAllocatedProgramme=k1+1;
									merit.candidates.get(j1).allocated_programme=allprogram.get(merit.candidates.get(j1).pref_list.get(k1).concat(String.valueOf(i1))).Code;
									merit.candidates.get(j1).countOfPref=merit.candidates.get(j1).indexOfAllocatedProgramme;
							   }
						   }
					   }
				   }
				   c=c+merit.Count[i1];
			   }
			}
	}
	/**
	 * this function prints the final output to Output1.csv file.
	 */
	public void print(){
		
		FileWriter out= null;
		try{
			out=new FileWriter("Output1.csv");
			HashSet<String> date=new HashSet<String>();
			out.append("CandidateUniqueId,ProgrammeID \n");
			for(int i=0;i<this.merit.candidates.size();i++){
				if(!date.contains(merit.candidates.get(i).studentID)){
					out.append(this.merit.candidates.get(i).studentID);
					date.add(this.merit.candidates.get(i).studentID);
					out.append(",");
					out.append(this.merit.candidates.get(i).allocated_programme);
					out.append("\n");
				}
			}
			out.flush();
			out.close();
		}
		catch(IOException e)
		{
			 e.printStackTrace();
		}
	}
}
