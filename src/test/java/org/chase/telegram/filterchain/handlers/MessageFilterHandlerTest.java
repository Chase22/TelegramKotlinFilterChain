package org.chase.telegram.filterchain.handlers;

import org.chase.telegram.filterchain.Handler;
import org.chase.telegram.filterchain.UpdateContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageFilterHandlerTest {

    @Mock
    Update updateMock;

    @Mock
    Handler handlerMock;

    @Mock
    UpdateContext updateContextMock;

    MessageFilterHandler sut = new MessageFilterHandler();

    @BeforeEach
    void init() {
        lenient().when(updateContextMock.getUpdate()).thenReturn(updateMock);
        lenient().when(handlerMock.call(updateContextMock)).thenReturn(updateContextMock);
    }

    @Test
    void shouldCallNextHandlerIfUpdateHasAMessage() {
        sut.setNext(handlerMock);
        when(updateMock.hasMessage()).thenReturn(true);

        sut.call(updateContextMock);

        verify(handlerMock, times(1)).call(updateContextMock);

    }

    @Test
    void shouldNotCallNextHandlerIfNoHandlerWasSet() {
        when(updateMock.hasMessage()).thenReturn(true);

        sut.call(updateContextMock);

        verifyNoInteractions(handlerMock);
    }

    @Test
    void shouldNotCallNextHandlerIfUpdateHasNoMessage() {
        sut.setNext(handlerMock);

        when(updateMock.hasMessage()).thenReturn(false);

        sut.call(updateContextMock);

        verifyNoInteractions(handlerMock);
    }
}
