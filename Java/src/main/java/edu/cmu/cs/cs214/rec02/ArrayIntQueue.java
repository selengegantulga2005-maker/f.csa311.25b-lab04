package edu.cmu.cs.cs214.rec02;

import java.util.Arrays;

/**
 * ArrayIntQueue – IntQueue интерфэйсийн массив дээр суурилсан хэрэгжилт.
 * Доорх кодонд эх хувилбар дахь 3 алдааг зассан.
 */
public class ArrayIntQueue implements IntQueue {

    private int[] elementData;   // Массивт хадгалах өгөгдлүүд
    private int head;            // Эхний элемент рүү заах индекс
    private int size;            // Одоогийн урт
    private static final int INITIAL_SIZE = 10;

    public ArrayIntQueue() {
        elementData = new int[INITIAL_SIZE];
        head = 0;
        size = 0;
    }

    @Override
    public void clear() {
        Arrays.fill(elementData, 0);
        size = 0;
        head = 0;
    }

    @Override
    public Integer dequeue() {
        if (isEmpty()) return null;

        Integer value = elementData[head];
        head = (head + 1) % elementData.length; // Эргэлтийн индекс
        size--;
        return value;
    }

    @Override
    public boolean enqueue(Integer value) {
        ensureCapacity();
        int tail = (head + size) % elementData.length;
        elementData[tail] = value;
        size++;
        return true;
    }

    @Override
    public boolean isEmpty() {
        /**
         * ❌ BUG #1 – Буруу нөхцөл
         * 
         * Эх код:
         *     return size >= 0;
         * 
         * Учир:
         *     size хэзээ ч сөрөг болдоггүй → энэ нь үргэлж TRUE болно.
         *     Өөрөөр хэлбэл queue хоосон эсэхийг шалгаж чадахгүй.
         *
         * ✔ Шийдэл:
         *     Жинхэнэ хоосон нөхцөл → size == 0
         */
        return size == 0;
    }

    @Override
    public Integer peek() {
        /**
         * ❌ BUG #2 – Хоосон үед NULL буцаахгүй байсан
         *
         * Эх код:
         *     return elementData[head];
         *
         * Учир:
         *     Queue хоосон байхад elementData[0] → 0 буцаана.
         *     Энэ нь IntQueue тодорхойлолтыг зөрчинө.
         *
         * ✔ Шийдэл:
         *     Хоосон бол null буцаана.
         */
        if (isEmpty()) return null;
        return elementData[head];
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Queue–д зай хүрэхгүй үед багтаамжийг 2 дахин нэмэгдүүлнэ.
     */
    private void ensureCapacity() {
        if (size == elementData.length) {

            /**
             * ❌ BUG #3 – Массивыг нэмэх үед буруу хуулдаг байсан
             *
             * Эх кодонд хоёр давталттай, буруу индекс тооцдог байсан:
             *   - wrap-around үед элементүүдийн дараалал алдагдана
             *   - FIFO дараалал эвдэрнэ
             *
             * ✔ Шийдэл:
             *   Queue–ийн логик дарааллын дагуу хуулна:
             *     new[0] = old[head]
             *     new[1] = old[(head + 1) % len]
             *     ...
             *     new[size-1] = old[(head + size - 1) % len]
             *
             *   Мөн дараа нь head = 0 болгоно.
             */

            int oldCapacity = elementData.length;
            int newCapacity = oldCapacity * 2 + 1;
            int[] newData = new int[newCapacity];

            // Логик дарааллаар хуулж шинэ массивт байрлуулах
            for (int i = 0; i < size; i++) {
                newData[i] = elementData[(head + i) % oldCapacity];
            }

            elementData = newData;
            head = 0;
        }
    }
}