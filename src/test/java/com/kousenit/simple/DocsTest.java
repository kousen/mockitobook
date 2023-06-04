package com.kousenit.simple;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DocsTest {
    // "Let's verify some behaviour!"
    // https://javadoc.io/static/org.mockito/mockito-core/5.3.1/org/mockito/Mockito.html#verification
    @Test
    void testFromDocumentation() {
        //mock creation
        List<String> mockedList = mock();

        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();

    }
}
