import java.io.*;
import java.util.*;

public class Rank {
public float[] rank;
public Rank(){
	this.rank=new float[3];
	for(int i=0;i<3;i++)rank[i]=0;
}
public Rank(float rank1,float rank2,float rank3){
	rank[0]=rank1;rank[1]=rank2;rank[2]=rank3;
}
}
