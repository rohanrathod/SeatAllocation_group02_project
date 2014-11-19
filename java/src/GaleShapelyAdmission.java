import java.io.*;
import java.util.*;
/**
 * 
 * @author Team Strikers 
 * this is the programme which implements seat allocation on the basis of galeShapely Algorithms
 */
/**
 * this class stores the inputs and the necessary data
 * it implements and prints the algorithmically derived seat allocation for candidates
 */
public class GaleShapelyAdmission {
	
	public ArrayList<Candidate>allCandidate;
	public HashMap<String,VirtualProgramme> allProgram;
	public meritlist merit;
	public DSmeritlist DSmerit;
	public HashMap<String,Integer> institutes;
	/**
	 * this is the basic constructor .
	 * it does alll the work and the final result is stored in the data members
	 */
	public GaleShapelyAdmission(){
		this.institutes=new HashMap<String,Integer>();
		this.allCandidate=new ArrayList<Candidate>();
		this.allProgram=new HashMap<String,VirtualProgramme>();
		this.merit=new meritlist();
		this.DSmerit=new DSmeritlist();
		//////////////////////////////////////////////////////////// 
		String csvFileToRead = "programs.csv";  
		BufferedReader br = null;  
		String line = "",line1;  
		String splitBy = ",";  
		/**
		 * we take the input of the program available and creates respective virtual programme
		 * we store all the virtual programme
		 */
		try{
			
		br = new BufferedReader(new FileReader(csvFileToRead));
		line=br.readLine();
		while ((line = br.readLine()) != null) {  
			String[] input = line.split(splitBy);
			allProgram.put(input[1].concat("0"),new VirtualProgramme(input[1],input[2],0,"N",Integer.parseInt(input[3])));
			allProgram.put(input[1].concat("1"),new VirtualProgramme(input[1],input[2],1,"N",Integer.parseInt(input[4])));
			allProgram.put(input[1].concat("2"),new VirtualProgramme(input[1],input[2],2,"N",Integer.parseInt(input[5])));
			allProgram.put(input[1].concat("3"),new VirtualProgramme(input[1],input[2],3,"N",Integer.parseInt(input[6])));
			allProgram.put(input[1].concat("4"),new VirtualProgramme(input[1],input[2],0,"Y",Integer.parseInt(input[7])));
			allProgram.put(input[1].concat("5"),new VirtualProgramme(input[1],input[2],1,"Y",Integer.parseInt(input[8])));
			allProgram.put(input[1].concat("6"),new VirtualProgramme(input[1],input[2],2,"Y",Integer.parseInt(input[9])));
			allProgram.put(input[1].concat("7"),new VirtualProgramme(input[1],input[2],3,"Y",Integer.parseInt(input[10])));
		   this.institutes.put(input[1].substring(0,1),0);
		}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		csvFileToRead="choices.csv";
		BufferedReader cr = null; 
		String csvFileToRead1="ranklist.csv";
/////////////////////////////////////////////////////////////////////////////////////////
		/**
		 * now we take the candidates data as input
		 */
		try{
		   cr = new BufferedReader(new FileReader(csvFileToRead1));
		   line1=cr.readLine();
		   while((line1 = cr.readLine()) != null){
				String[] input1 = line1.split(splitBy);
				float[] r=new float[3];
				int k=1;
				for(int i=0;i<3;i++)r[i]=0;
				r[0]=Float.parseFloat(input1[3]);
				for(int i=4;i<11;i++){
					if(input1[i].charAt(0)!='0'){
						r[k]=Float.parseFloat(input1[i]);k++;}
				}
				this.merit.insert(input1[0],r[0],r[1],r[2]);
				//allProgram.get(c1.next_preference).insertInWaitlist(c1);
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
		 * here we store the total data of candidates 
		 */
		try{
			
			br = new BufferedReader(new FileReader(csvFileToRead));
			line=br.readLine();
			while ((line = br.readLine()) != null) {  
				String[] input = line.split(splitBy);
				Candidate c1=new Candidate(input[0],input[1],input[2],this.merit.map.get(input[0]).rank[0],this.merit.map.get(input[0]).rank[1],this.merit.map.get(input[0]).rank[2]);
				c1.add_pref(input[3]);
				c1.addVirtualPref();
				this.allCandidate.add(c1);
				if(input[1].equals("DS")){
					DSmerit.insert(c1);
				}
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally 
		{
			if (br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//let's process some DS cases first
		for(int i=0;i<DSmerit.candidates.size();i++){
			//implementation here
			for(int j=0;j<DSmerit.candidates.get(i).pref_list.size();i++){
				if(this.institutes.get(DSmerit.candidates.get(i).pref_list.get(j).charAt(0))==null){
					DSmerit.candidates.get(i).countOfPref=j+1;
					DSmerit.candidates.get(i).allocated_programme=(DSmerit.candidates.get(i).pref_list.get(j)).concat("0");
					DSmerit.candidates.get(i).pref_list=new ArrayList<String>(DSmerit.candidates.get(i).pref_list.subList(0,j));
					this.allProgram.get((DSmerit.candidates.get(i).pref_list.get(j)).concat("0")).DS_waitlist.add(DSmerit.candidates.get(i));
					DSmerit.candidates.get(i).addVirtualPref();
					this.institutes.put(DSmerit.candidates.get(i).pref_list.get(0).substring(0,1),1);
					break;
				}
				if(this.institutes.get(DSmerit.candidates.get(i).pref_list.get(j).charAt(0))==0){
					DSmerit.candidates.get(i).countOfPref=j+1;
					DSmerit.candidates.get(i).allocated_programme=(DSmerit.candidates.get(i).pref_list.get(j)).concat("0");
					DSmerit.candidates.get(i).pref_list=new ArrayList<String>(DSmerit.candidates.get(i).pref_list.subList(0,j));
					this.allProgram.get((DSmerit.candidates.get(i).pref_list.get(j)).concat("0")).DS_waitlist.add(DSmerit.candidates.get(i));
					DSmerit.candidates.get(i).addVirtualPref();
					this.institutes.put(DSmerit.candidates.get(i).pref_list.get(0).substring(0,1),1);
					break;
				}
				if(this.institutes.get(DSmerit.candidates.get(i).pref_list.get(0).charAt(0))==1){
					DSmerit.candidates.get(i).countOfPref=j+1;
					DSmerit.candidates.get(i).allocated_programme=DSmerit.candidates.get(i).pref_list.get(j);
					DSmerit.candidates.get(i).pref_list=new ArrayList<String>(DSmerit.candidates.get(i).pref_list.subList(0,j));
					this.DSmerit.candidates.get(i).addVirtualPref();
					this.allProgram.get(DSmerit.candidates.get(i).pref_list.get(j)).DS_waitlist.add(DSmerit.candidates.get(i));
					this.institutes.put(DSmerit.candidates.get(i).pref_list.get(0).substring(0,1),2);
					break;
				}
			}
		}
		
		/**
		 * now,we start processing the algorithm in order to get the assigned result for all candidates
		 */
		while(candidatesApply()){
			refreshList();
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * this function moves to the next_preference of the rejected candidates
	 */
	public boolean candidatesApply(){
		Integer tr=0;
		Integer fal=0;
		for(int i=0;i<this.allCandidate.size();i++){
			if(this.allCandidate.get(i).set_next_preference()){
				tr=tr+1;
			}
			else{fal++;}
			this.allProgram.get(this.allCandidate.get(i).pref_list1.get(this.allCandidate.get(i).indexOfAppliedProgs)).addInWaitlist(this.allCandidate.get(i));
		}
		if(tr.equals(0))return false;
		else return true;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * this function refresh and reassign the candidates corresponding to all the virtual programme
	 */
	public void refreshList(){
		this.allCandidate.clear();
		for(String key:this.allProgram.keySet()){
			this.allProgram.get(key).sort();
			ArrayList<Candidate> c=this.allProgram.get(key).filter();
			for(int i=0;i<c.size();i++){
				this.allCandidate.add(c.get(i));
			}
		}
	}
	/**
	 * this function prints the output for gale shapely algorithm
	 */
	public void output(){
		HashSet<String> date=new HashSet<String>();
		FileWriter out= null;
		try{
				out=new FileWriter("Output.csv");
				out.append("CandidateUniqueId,ProgrammeID \n");
				for(String key:this.allProgram.keySet()){
					for(int i=0;i<this.allProgram.get(key).waitlist.size();i++){
						if(this.allProgram.get(key).waitlist.get(i).category.equals("DS")){
							if((this.allProgram.get(key).waitlist.get(i)).indexOfAllocatedProgramme!=(this.allProgram.get(key).waitlist.get(i)).DS_count){
								
								if(!date.contains(this.allProgram.get(key).waitlist.get(i).studentID)){
									out.append(this.allProgram.get(key).waitlist.get(i).studentID);
									date.add(this.allProgram.get(key).waitlist.get(i).studentID);
									out.append(",");
									out.append(key.substring(0,key.length()-1));
									out.append("\n");
								}
							}
							else{
								if(!date.contains(this.allProgram.get(key).waitlist.get(i).studentID)){
									out.append(this.allProgram.get(key).waitlist.get(i).studentID);
									date.add(this.allProgram.get(key).waitlist.get(i).studentID);
									out.append(",");
									String s=this.allProgram.get(key).waitlist.get(i).pref_list.get(this.allProgram.get(key).waitlist.get(i).DS_count);
									out.append(s.substring(0,s.length()-1));
									out.append("\n");
								}
							}
						}
						else{
							if(!date.contains(this.allProgram.get(key).waitlist.get(i).studentID)){
								out.append(this.allProgram.get(key).waitlist.get(i).studentID);
								date.add(this.allProgram.get(key).waitlist.get(i).studentID);
								out.append(",");
								out.append(key.substring(0,key.length()-1));
								out.append("\n");
							}
						}
					}
				}
				out.flush();
				for(int i=0;i<this.allCandidate.size();i++){
					out.append(this.allCandidate.get(i).studentID);
					out.append(",-1 \n");
					out.flush();
				}
				out.close();
			}
			catch(IOException e)
			{
				 e.printStackTrace();
			}
		
	}
}