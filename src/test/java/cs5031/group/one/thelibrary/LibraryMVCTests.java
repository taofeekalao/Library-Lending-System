package cs5031.group.one.thelibrary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// Note: test for end-end web client if we build one
@SpringBootTest
@AutoConfigureMockMvc
public class LibraryMVCTests {

    @Autowired
    private MockMvc mockMvc;

    // A mock end-end test for the REST api call /borrow (a book)
    // this is to allow the client implementation to be done in paralell
    @SuppressWarnings("null")
    @Test
    public void testBorrowEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/borrowed")
                        .param("isbn", "9780743273565")
                        .param("member_Id", "00001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Book borrowed"))
                .andDo(MockMvcResultHandlers.print());
    }

    // A mock end-end test for the REST api call /return (a book)
    // this is to allow the client implementation to be done in paralell
    @SuppressWarnings("null")
    @Test
    public void testReturnEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/returned")
                        .param("isbn", "9780743273565")
                        .param("member_Id", "00001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Book returned"))
                .andDo(MockMvcResultHandlers.print());
    }

}
