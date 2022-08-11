package com.example.calcioconlaf;

import android.util.Log;

import java.util.ArrayList;

public class SetRisposteThread extends Thread{

    ArrayList<Quiz> domande;
    ArrayList<String> opzioni;

    public SetRisposteThread(ArrayList<Quiz> domande, ArrayList<String> opzioni) {
        this.domande=domande;
        this.opzioni=opzioni;
    }

    @Override
    public void run() {
        super.run();
        addOption();
    }

    public void addOption() {
        Log.v("size1", String.valueOf(opzioni.size()));
        int cont=0;
        for (int i = 0; i < domande.size(); i++) {
            for (int a = 0; a < 3; a++) {
                if ((!(domande.get(i).getOption1().equals(opzioni.get(cont))))
                        && (!(domande.get(i).getOption2().equals(opzioni.get(cont))))
                        && (!(domande.get(i).getOption3().equals(opzioni.get(cont)))
                        && (!(domande.get(i).getOption4().equals(opzioni.get(cont)))))) {
                    switch (a) {
                        case 0:
                            domande.get(i).setOption1(opzioni.get(cont));
                            opzioni.remove(cont);
                            if(cont!=0){
                                cont=0;
                            }
                            break;
                        case 1:
                            domande.get(i).setOption2(opzioni.get(cont));
                            opzioni.remove(cont);
                            if(cont!=0){
                                cont=0;
                            }
                            break;
                        case 2:
                            domande.get(i).setOption3(opzioni.get(cont));
                            opzioni.remove(cont);
                            if(cont!=0){
                                cont=0;
                            }
                            break;
                    }
                }
                else{
                    cont++;
                    a--;
                }

            }

        }
    }
}
