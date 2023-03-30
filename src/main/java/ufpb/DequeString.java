package ufpb;

import java.util.Iterator;
import java.util.ListIterator;

public class DequeString implements Iterable<String>{
    private int n;
    private No Sentinela;

    public DequeString() {
        n = 0;
        Sentinela = new No();
        Sentinela.prox = Sentinela;
        Sentinela.ant = Sentinela;
    }

    private class No {
        private String dado;
        private No prox;
        private No ant;
    }

    public void push_front(String item) {
        No temp = new No();
        temp.dado = item;
        temp.prox = Sentinela.prox;
        temp.ant = Sentinela;
        Sentinela.prox = temp;
        temp.prox.ant = temp;
        n++;
    }
    public void push_back(String item) {
        No temp = new No();
        temp.dado = item;
        temp.prox = Sentinela;
        temp.ant = Sentinela.ant;
        Sentinela.ant = temp;
        temp.ant.prox = temp;
        n++;
    }

    public String pop_front() {
        No temp = Sentinela.prox;
        String meuDado = temp.dado;
        temp.ant.prox = temp.prox;
        temp.prox.ant = temp.ant;
        n--;
        return meuDado;
    }

    public String pop_back() {
        No temp = Sentinela.ant;
        String meuDado = temp.dado;
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
    public Iterator<String> iterator() {
        return null;
    }

    public class DequeIterator implements ListIterator<String>  {
        private No atual = Sentinela.prox;
        private int indice = 0;
        private No acessadoultimo = null;

        @Override
        public boolean hasNext() {
            return indice < n;
        }

        @Override
        public String next() {
            if (!hasNext()) return null;
            String meuDado = atual.dado;
            acessadoultimo = atual;
            atual = atual.prox;
            indice++;
            return meuDado;
        }

        @Override
        public boolean hasPrevious() {
            return indice > 0;
        }

        @Override
        public String previous() {
            if (!hasPrevious()) return null;
            atual = atual.ant;

            String meuDado = atual.dado;
            acessadoultimo = atual;
            indice--;
            return meuDado;
        }

        @Override
        public int nextIndex() {
            return indice;
        }

        @Override
        public int previousIndex() {
            return indice - 1;
        }

        @Override
        public void remove() {
            if (acessadoultimo == null) throw new IllegalStateException();
            acessadoultimo.ant.prox = acessadoultimo.prox;
            acessadoultimo.prox.ant = acessadoultimo.ant;
            n--;
            if (atual == acessadoultimo) {
                atual = acessadoultimo.prox;
            } else {
                indice--;
            }
            acessadoultimo = null;
        }

        @Override
        public void set(String string) {
            if (acessadoultimo == null) throw new IllegalStateException();
            acessadoultimo.dado = string;
        }

        @Override
        public void add(String string) {
            No temp = new No();
            temp.dado = string;
            temp.prox = atual.prox;
            temp.ant = atual;
            atual.prox.ant = temp;
            atual.prox = temp;
            n++;
        }

        public String get() {
            if (acessadoultimo == null) throw new IllegalStateException();
            return atual.dado;
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (String item : this) {
            s.append(item + " ");
        }
        return s.toString();
    }
}
