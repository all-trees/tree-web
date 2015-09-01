package nl.dvberkel.controller;

import nl.dvberkel.context.TreeContextConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.*;

import static org.apache.commons.io.IOUtils.copy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TreeContextConfiguration.class })
public class TreeControllerTest {
    private static final String SVG_PATHNAME = "src/test/resources/tree-controller-test.svg";

    @Autowired
    private TreeController controller;

    private String expectedSvg;
    private MockMvc mockMvc;

    @Before
    public void readExpectedLeafSvgFromFile() throws IOException {
        expectedSvg = contentOf(SVG_PATHNAME);
    }

    private String contentOf(String pathname) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(pathname));
        StringWriter writer = new StringWriter();
        copy(inputStream, writer, "utf-8");

        return writer.toString();
    }


    @Before
    public void createMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldHaveAutowiredController() {
        assertThat(controller, is(notNullValue()));
    }

    @Test
    public void shouldHaveCreatedMockMvc() {
        assertThat(mockMvc, is(notNullValue()));
    }

    @Test
    public void shouldStatusNotFoundOnNotADyckWord() throws Exception {
        mockMvc.perform(get("/(")).andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnStatusOkOnCorrectDyckWord() throws Exception {
        mockMvc.perform(get("/()()"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "image/svg+xml"));
    }

    @Test
    public void shouldReturnSvgOnCorrectDyckWord() throws Exception {
        MvcResult result = mockMvc.perform(get("/()()")).andReturn();

        String response = result.getResponse().getContentAsString();

        assertThat(response, is(expectedSvg));
    }
}
