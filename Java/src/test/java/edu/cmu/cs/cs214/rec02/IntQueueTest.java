package edu.cmu.cs.cs214.rec02;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * IntQueue интерфэйсийн тодорхойлолтод суурилсан тестүүд.
 * (LinkedIntQueue → Black-box)
 * (ArrayIntQueue → White-box)
 */
public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    @Before
    public void setUp() {
        // Алхам 1: LinkedIntQueue дээр тестлэнэ
     // mQueue = new LinkedIntQueue();
        mQueue = new ArrayIntQueue();
        // Алхам 2 дээр ArrayIntQueue болгож сольж тестлэнэ
        // mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    /**
     * Queue шинээр үүсэх үед хоосон байх ёстой.
     */
    @Test
    public void testIsEmpty() {
        assertTrue(mQueue.isEmpty());
        assertEquals(0, mQueue.size());
        assertNull(mQueue.peek());
        assertNull(mQueue.dequeue());
    }

    /**
     * Хоосон биш болох нөхцөл (1 элемент нэмэх).
     */
    @Test
    public void testNotEmpty() {
        mQueue.enqueue(10);
        assertFalse(mQueue.isEmpty());
        assertEquals(1, mQueue.size());
        assertEquals(Integer.valueOf(10), mQueue.peek());
    }

    /**
     * peek() хоосон үед null буцаах ёстой.
     */
    @Test
    public void testPeekEmptyQueue() {
        assertNull(mQueue.peek());
        assertTrue(mQueue.isEmpty());
    }

    /**
     * peek() хоосон биш үед эхний элемент буцаана (устгахгүй).
     */
    @Test
    public void testPeekNoEmptyQueue() {
        mQueue.enqueue(5);
        assertEquals(Integer.valueOf(5), mQueue.peek());
        // peek() size-г бууруулдаггүй
        assertEquals(1, mQueue.size());
    }

    /**
     * FIFO шалгах: эхэлж орсон эхэлж гарна.
     */
    @Test
    public void testDequeue() {
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.enqueue(3);

        assertEquals(Integer.valueOf(1), mQueue.dequeue());
        assertEquals(Integer.valueOf(2), mQueue.dequeue());
        assertEquals(Integer.valueOf(3), mQueue.dequeue());
        assertTrue(mQueue.isEmpty());
    }

    /**
     * Файлаас уншсан өгөгдлүүд зөв дарааллаар гарах ёстой.
     * (Өгөгдсөн example тест – өөрчлөлтгүй)
     */
    @Test
    public void testContent() throws IOException {

        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }
}