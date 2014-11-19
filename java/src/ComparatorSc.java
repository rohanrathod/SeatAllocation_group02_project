import java.util.Comparator;

public class ComparatorSc implements Comparator<Candidate> {
	@Override
public int compare(Candidate f1,Candidate f2) {
		if(f2.rank[2]==0&&f2.rank[2]==0){
			if(f2.rank[0]==0)return -1;
			if(f1.rank[0]==0)return 1;
			if(f1.rank[0]>f2.rank[0])return 1;
			if(f1.rank[0]<f2.rank[0])return -1;
			else return 0;
		}
if(f2.rank[2]==0)return -1;
if(f1.rank[2]==0)return 1;
if(f1.rank[2]>f2.rank[2])return 1;
if(f1.rank[2]<f2.rank[2])return -1;
else return 0;
} 
}