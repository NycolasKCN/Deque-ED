package ufpb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ListIterator;
import java.util.StringTokenizer;

public class DequeCity implements Iterable<Cidade>{
    private int n;
    private No Sentinela;

    public DequeCity(){
        n = 0;
        Sentinela = new No();
        Sentinela.prox = Sentinela;
        Sentinela.ant = Sentinela;
    }

    private class No {
        private Cidade dado;
        private String chave;
        private No prox;
        private No ant;
    }

    public void push_front(String key, Cidade item) {
        No temp = new No();
        temp.dado = item;
        temp.chave = key;

        temp.ant = Sentinela;
        temp.prox = Sentinela.prox;

        Sentinela.prox = temp;
        temp.prox.ant = temp;
        n++;
    }

    public void push_back(String key, Cidade item) {
        No temp = new No();
        temp.dado = item;
        temp.chave = key;

        temp.ant = Sentinela.ant;
        temp.prox = Sentinela;

        Sentinela.ant = temp;
        temp.ant.prox = temp;
        n++;
    }

    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("Argument to contains() is null");
        return get(key) != null;
    }

    public Cidade get(String key) {
        if (key == null) throw new IllegalArgumentException("Argument to get() is null");
        for (No x = Sentinela.prox; x != Sentinela; x= x.prox) {
            if (key.equals(x.chave)) return x.dado;
        }
        return null;
    }

    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("Argument to delete() is null");
        delete(Sentinela.prox, key);
    }

    private void remove(No temp) {
        temp.ant.prox = temp.prox;
        temp.prox.ant = temp.ant;
        n--;
    }

    private void delete(No x, String key) {
        if (x == Sentinela) return;
        if (key.equals(x.chave)) {
            remove(x);
            return;
        }
        delete(x.prox, key);
    }

    public Cidade pop_front() {
        No temp = Sentinela.prox;
        Cidade meuDado = temp.dado;
        temp.ant.prox = temp.prox;
        temp.prox.ant = temp.ant;
        n--;
        return meuDado;
    }

    public Cidade pop_back() {
        No temp = Sentinela.ant;
        Cidade meuDado = temp.dado;
        temp.ant.prox = temp.prox;
        temp.prox.ant = temp.ant;
        n--;
        return meuDado;
    }

    public No first() {
        if (Sentinela == Sentinela.prox) return null;
        return Sentinela.prox;
    }

    public boolean isEmpty(){
        return n == 0;
    }

    public int size() {
        return n;
    }

    @Override
    public ListIterator<Cidade> iterator(){
        return new DequeIterator();
    }

    public class DequeIterator implements ListIterator<Cidade> {
        private No atual = Sentinela.prox;
        private int indice = 0;
        private No acessadoultimo = null;

        public boolean hasNext(){
            return indice < n;
        }

        public boolean hasPrevious(){
            return indice > 0;
        }

        public int previousIndex(){
            return indice - 1;
        }

        public int nextIndex() {
            return indice;
        }

        @Override
        public Cidade next() {
            if (!hasNext()) return null;

            Cidade meuDado = atual.dado;
            acessadoultimo = atual;
            atual = atual.prox;
            indice++;
            return meuDado;
        }

        @Override
        public Cidade previous() {
            if (!hasPrevious()) return null;
            atual = atual.ant;
            Cidade meuDado = atual.dado;
            acessadoultimo = atual;
            indice --;
            return meuDado;
        }

        @Override
        public void set(Cidade cidade) {
            if (acessadoultimo == null) throw new IllegalStateException();
            acessadoultimo.dado = cidade;
        }

        public Cidade get() {
            if (atual == null) throw new IllegalStateException();
            return atual.dado;
        }

        @Override
        public void remove() {
            if (acessadoultimo == null) throw new IllegalStateException();
            acessadoultimo.ant.prox = acessadoultimo.prox;
            acessadoultimo.prox.ant = acessadoultimo.ant;
            n--;
            if (atual == acessadoultimo){
                atual = acessadoultimo.prox;
            } else {
                indice--;
            }
            acessadoultimo = null;
        }

        @Override
        public void add(Cidade cidade) {
            No temp = new No();
            temp.dado = cidade;
            temp.prox = atual.prox;
            temp.ant = atual;

            temp.prox.ant = temp;
            atual.prox = temp;
            n++;
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Cidade item : this) {
            s.append(item + " ");
        }
        return s.toString();
    }

    public Iterable<String> keys() {
        DequeString queue = new DequeString();
        for (No x = Sentinela.prox; x != Sentinela; x = x.prox){
            queue.push_back(x.chave);
        }
        return queue;
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("\n\nUso: java DequeCity arquivo-1 arquivo-2\n\n");
            System.exit(0);
        }
        try {
            FileReader in1 = new FileReader(args[0]);
            BufferedReader br = new BufferedReader(in1);
            int total = Integer.parseInt(br.readLine());

            int temperature = 0;
            System.out.println("Total = "+total);
            DequeCity st = new DequeCity();

            for (int i = 0; i<total; i++) {
                String tmp = br.readLine();
                StringTokenizer tk = new StringTokenizer(tmp);
                String key = tk.nextToken();
                temperature = Integer.parseInt(tk.nextToken());
                Cidade myCity = new Cidade(key, temperature);
                st.push_back(key, myCity);
            }
            br.close();
            in1.close();
            System.out.println("----- Testando --- Procure afterword");
            System.out.println(st.get("afterword"));
            System.out.println("----- Testando --- Procure Feeney");
            System.out.println(st.get("Feeney"));
            System.out.println("----- Testando --- Procure Fee");
            System.out.println(st.get("Fee"));

            in1 = new FileReader(args[1]);
            br = new BufferedReader(in1);

            total = Integer.parseInt(br.readLine());
            for(int i = 0; i< total; i++){
                String tmp = br.readLine();
                StringTokenizer tk = new StringTokenizer(tmp);
                Cidade myCity = st.get(tk.nextToken());

                if (myCity == null) {
                    System.out.println("\n[Failed] " + tmp + " não foi encontrada.");
                } else {
                    System.out.println("\n[Ok] " + tmp + " foi encontrada. Temperatura lá é: " + myCity.getTemp() + "F");
                }
            }
            br.close();
            in1.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
