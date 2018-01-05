package ua.nure.sokolov.Practice2;


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyListImpl implements MyList, ListIterable{

    private final static int initSize = 7;
    private Object[] array = new Object[initSize];
    private int index = 0;
    int size = 0;

    @Override
    public void add(Object e) {
        if (array[index] == null){
            array[index] = e;
        }else if (array[index] == e){
            array[index+1] = e;
        }
        if (index == array.length-1){
            resize(array.length * 2);
        }
        index++;
        size++;
    }

    private void resize(int newLength) {
        Object[] newArray = new Object[newLength];
        System.arraycopy(array, 0, newArray, 0, index+1);
        array = newArray;
    }

    @Override
    public void clear() {

        for (int i = 0; i < size; i++) {
            array[i] = null;
        }

        size = 0;

    }

    @Override
    public boolean remove(Object o) {

        for(int i = 0; i < array.length; i++){
            try {
                if (o != null){
                    if(o.equals(array[i])){
                        array[size] = null;
                        size--;
                        return true;
                    }
                }
            }catch (NullPointerException e){
                System.out.print("");
            }
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] objArr = new Object[initSize];
        for (int j = 0; j < array.length; j++){
            objArr[j] = array[j];

        }

        return new String[]{convertToStr(objArr)};
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < array.length; i++) {
            try {
                if (o != null) {
                    if (o.equals(array[i])) {
                        return true;
                    }
                }
            }catch (NullPointerException npe){
                System.out.println("npe");
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(MyList c) {
        for (Object o : c){
            if (!contains(o)){
                return false;
            }
        }
        return true;
    }

    private static String convertToStr(Object[] strArray) {
        StringBuilder sb=new StringBuilder();
        int k=0;
        for(int i=0; i < strArray.length; i++) {
            if(strArray[i]!=null) {
                if(k > 0)
                    sb.append(", ");
                sb.append(strArray[i]);
                k++;
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {

        return "[" + convertToStr(array) + "]";
    }


    @Override
    public Iterator<Object> iterator() {
        return new IteratorImpl();
    }

    private class IteratorImpl implements Iterator<Object> {

        int cursor = 0;
        int lastRet = -1;
        private boolean calledNext;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public Object next() {
//            Object result = array[cursor];
//            cursor = cursor + 1;
//            return result;

            int i = cursor;
            if (i >= array.length){
                throw new NoSuchElementException();
            }
            if (i >= array.length){
                throw new ConcurrentModificationException();
            }
            cursor = i + 1;
            calledNext = true;
            return array[i];
        }

        @Override
        public void remove() {
            if (!calledNext) {
                throw new IllegalStateException();
            }
            boolean returnRemove = MyListImpl.this.remove(array[cursor]);

            cursor--;
            if (calledNext) {
                for (int i = 0; i < array.length; i++) {
                    if (array[cursor] != null) {
                        if (array[cursor].equals(array[i])) {
                            array[cursor] = null;
                            size--;
                        }
                    }
                }
            }

            calledNext = false;
            cursor++;
        }
    }

    @Override
    public ListIterator listIterator() {
        return new ListIteratorImpl();
    }


    private class ListIteratorImpl extends IteratorImpl implements ListIterator {

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public Object previous() {
            try {
                int i = cursor - 1;
                Object previous = get(i);
                lastRet = cursor = i;
                return previous;
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void set(Object e) {
//            if (lastRet < 0) {
//                throw new IllegalStateException();
//            }
//            try {
//             MyListImpl.this.set(lastRet, e);
//
//            } catch (IndexOutOfBoundsException ex) {
//                throw new ConcurrentModificationException();
//            }
        }
        
        @Override
        public void remove() {
            super.remove();
        }
    }

    public Object get(int num){
        return array[num];
    }

    @Override
    public Object set(int index, Object object) {
        Object oldObject = array[index];
        array[index] = object;
        return oldObject;
    }
}
