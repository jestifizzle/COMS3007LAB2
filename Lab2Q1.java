import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
        int[] Target = new int[arr.size()];
        for (int j = 0; j < arr.size(); j++){
            double[] t = arr.get(j);
            Target[j] = (int)t[rowlength-1];
            t[rowlength-1] = -1;
            arr.set(j, t);
        }
        double min = -1;
        double max = 1;
        double[] weight = new double[rowlength];
        Random r = new Random();
        for (int k = 0; k < weight.length; k++){
            double result = min + (r.nextDouble()*(max - min));
            weight[k] = (double)Math.round(result*10000d)/10000d;
        }
        double rate = 0.25;

        double[] learntWeight = PerceptLearn(arr, Target, weight, rate);

        for (int m = 0; m < learntWeight.length; m++){
            System.out.println(learntWeight[m]);
        }
        in.close();
    }

    public static double[] PerceptLearn(List<double[]> X, int[] T, double[] W, double n){
        int err = 0;
        for (int j = 0; j < X.size(); j++){
            double[] row = X.get(j);
            int Y = percept(W, row);
            err = err + Math.abs(T[j]-Y);
            for (int k = 0; k < row.length; k++){
                W[k] = (double)Math.round( (W[k] + (n*(T[j] - Y)*row[k]))*10000d )/10000d;
            }
            System.out.println("Error: " + err);
        }
        return W;
    }
}
