import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.*;

/**
 * Created by jesse on 2017/02/20.
 */
public class Lab2Q1 {
    public static int percept(double[] W, double[] X){
        int out;
        double[] r = new double[W.length];
        double result = 0;
        for (int i = 0; i < W.length; i++){
            r[i] = W[i]*X[i];
            result = result + r[i];
        }
        if (result <= 0){
            out = 0;
        } else {
            out = 1;
        }
        return out;
    }
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String line;
        List<double[]> arr = new ArrayList<double[]>();
        int rowlength = 0;
        while (!(line = in.nextLine()).isEmpty()){
            String[] inputS = line.split(" ");
            double[] inputX = new double[inputS.length];
            for (int i = 0; i < inputS.length; i++){
                inputX[i] = Double.parseDouble(inputS[i]);
            }
            arr.add(inputX);
            rowlength = inputX.length;
        }
        //System.out.println("Enter # of additional layers: ");
        //int Layers = in.nextInt();
        System.out.println("Enter # of 2nd layer nodes: ");
        int Nodes = in.nextInt();
        //int[] Layer2 = new int[Nodes+1];
        int[] Target = new int[arr.size()];
        for (int j = 0; j < arr.size(); j++){
            double[] t = arr.get(j);
            Target[j] = (int)t[rowlength-1];
            t[rowlength-1] = -1;
            arr.set(j, t);
        }
        double min = -1;
        double max = 1;
        List<double[]> weight = new ArrayList<double[]>();
        //double[] weight = new double[rowlength];
        Random r = new Random();
        for (int l = 0; l < Nodes; l++){
            double[] Wrow = new double[rowlength];
            for (int k = 0; k < rowlength; k++){
                double result = min + (r.nextDouble()*(max - min));
                Wrow[k] = (double)Math.round(result*10000d)/10000d;
            }
            weight.add(Wrow);
        }
        double rate = 0.25;

        List<int[]> learntWeight = PerceptLearn(arr, Target, weight, rate);

        for (int m = 0; m < learntWeight.size(); m++){
            System.out.println("Next layer (Y): ");
            int[] R = learntWeight.get(m);
            for (int n = 0; n < R.length; n++){
                System.out.println(R[n]);
            }
        }
        in.close();
    }

    public static List<int[]> PerceptLearn(List<double[]> X, int[] T, List<double[]> W, double n){
        List<int[]> Yresult = new ArrayList<>();
        List<Integer> Order = new ArrayList<>();
        for (int o = 0; o < X.size(); o++){
            Order.add(o);
        }
        Collections.shuffle(Order);
        //int err = 0;
        for (int j = 0; j < X.size(); j++){
            int[] Y = new int[W.size()+1];
            for (int x = 0; x < W.size(); x++){
                int O = Order.get(j);
                double[] row = X.get(O);
                double[] w = W.get(x);
                Y[x] = percept(w, row);
                //err = err + Math.abs(T[O]-Y);
                for (int k = 0; k < row.length; k++){
                    w[k] = (double)Math.round( (w[k] + (n*(T[O] - Y[x])*row[k]))*10000d )/10000d;
                }
                W.set(x,w);
            }
            Y[W.size()] = -1;
            Yresult.add(Y);
            //System.out.println("Error: " + err + " Index: " + O);
        }
        return Yresult;
    }
}
