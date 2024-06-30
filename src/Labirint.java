import java.io.*;
import java.util.Random;

import java.io.IOException;

import java.io.ObjectOutputStream;

public class Labirint {


        private char[][] harte;
        public int nrThesareveTeMbledhura = 0;
        public int piketEFituara=0;
        private int rreshta;
        private int kolona;
        int daljeRresht;
        int daljeKolone;
        private int madhesiaX = 20;
        private int madhesiaY = 20;

        public Labirint() {
            rreshta = 1;
            kolona = 0;
            harte = gjeneroLabirint();

            harte[rreshta][kolona] = 'X';
            Random random = new Random();

            daljeRresht = random.nextInt(madhesiaX);
            daljeKolone = random.nextInt(madhesiaY);
            harte[daljeRresht][daljeKolone] = '0';

        }

        public char[][] gjeneroLabirint() {
            char[][] labirint = new char[20][20];
            Random random = new Random();

            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    if (random.nextInt(4) == 0) {
                        labirint[i][j] = '|';
                    } else {
                        labirint[i][j] = '.';
                    }
                }
            }
            labirint[rreshta][kolona] = 'X';
            for (int i = 0; i < 5; i++) {
                int randomRresht, randomKolone;
                do {
                    randomRresht = random.nextInt(20);
                    randomKolone = random.nextInt(20);
                } while (labirint[randomRresht][randomKolone] != '.');
                labirint[randomRresht][randomKolone] = '*';
            }

            return labirint;
        }

        public void levizLojtar(String drejtimi) {
            if (drejtimi.equals("R") && kontrollDjathtas()) {
                leviz(0, 1);
            } else if (drejtimi.equals("L") && kontrollMajtas()) {
                leviz(0, -1);
            } else if (drejtimi.equals("U") && kontrollLart()) {
                leviz(-1, 0);
            } else if (drejtimi.equals("D") && kontrollPoshte()) {
                leviz(1, 0);
            }

        }
        public void afishoHarte() {
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    System.out.print(harte[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }

        public boolean Levizje(int levizRresht, int levizKolone) {
            int newRr = rreshta + levizRresht;
            int newK = kolona + levizKolone;

            if (newRr >= 0 && newRr < 20 && newK >= 0 && newK < 20 && harte[newRr][newK] != '|') {
                return true;
            } else {
                return false;
            }
        }

        public boolean kontrollDjathtas() {
            return Levizje(0, 1);
        }

        public boolean kontrollMajtas() {
            return Levizje(0, -1);
        }

        public boolean kontrollLart() {
            return Levizje(-1, 0);
        }

        public boolean kontrollPoshte() {
            return Levizje(1, 0);
        }

        private void leviz(int levizrreshtin, int levizkolonen) {
            int newRow = rreshta + levizrreshtin;
            int newCol = kolona + levizkolonen;

            if (Levizje(levizrreshtin, levizkolonen)) {
                if (harte[newRow][newCol] == '*') {
                    nrThesareveTeMbledhura++;
                    Lojtar.mesazhiThesareve(nrThesareveTeMbledhura);
                    piketEFituara+=5;
                    hiqThesar();
                }

                harte[rreshta][kolona] = '&';
                rreshta = newRow;
                kolona= newCol;
                harte[rreshta][kolona] = 'X';

                if (arriturNeDalje()) {
                    System.out.println("Urime, keni arritur deri ne dalje!");
                }
            }
        }

        public void levizDjathtas() {
            leviz(0, 1);
        }

        public void levizMajtas() {
            leviz(0, -1);
        }

        public void levizLart() {
            leviz(-1, 0);
        }

        public void levizPoshte() {
            leviz(1, 0);
        }

        public void afishoMesazhinEDaljes() {
            System.out.println("Urime, keni arritur deri ne dalje!");
        }

        private boolean lojaKaMbaruar = false;

        public boolean arritjeFitores() {
            return lojaKaMbaruar || (rreshta == 10 && kolona == 19);
        }

        public boolean arriturNeDalje() {
            return rreshta == daljeRresht && kolona == daljeKolone;
        }

        public boolean kontrollThesari() {
            return harte[rreshta][kolona] == '*';
        }

        public void hiqThesar() {
            harte[rreshta][kolona] = '.';
        }

        public char[][] getHarte() {
            return harte;
        }

        public int getRreshta(){
            return rreshta;
        }

        public int getKolona(){
            return kolona;
        }
        public void ruajLojen(){
            try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("labirinti.dat"))){
                output.writeObject(this);
                System.out.print("Loja u ruajt me sukses!");
            }catch(IOException e){
                e.printStackTrace();
  }

    }



}
