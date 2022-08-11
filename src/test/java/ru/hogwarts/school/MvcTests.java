package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school.Constants.*;

@WebMvcTest(controllers = FacultyController.class)
public class MvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @SpyBean
    private RecordMapper recordMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testFindFaculty() throws Exception {

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(TEST_FACULTY_1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/{id}", ID1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID1))
                .andExpect(jsonPath("$.name").value(NAME1))
                .andExpect(jsonPath("$.color").value(COLOR1));
    }

    @Test
    void testFindFacultyFilteredByColor() throws Exception {
        System.out.println(objectMapper.writeValueAsString(TEST_LIST));

        when(facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(any(String.class), any(String.class)))
                .thenReturn(TEST_LIST);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filter")
                        .queryParam("nameOrColor", COLOR1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(recordMapper.toRecord(TEST_FACULTY_21),recordMapper.toRecord(TEST_FACULTY_22)))));
    }

    @Test
    void testAddFaculty() throws Exception {

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", ID1);
        facultyObject.put("name", NAME1);
        facultyObject.put("color", COLOR1);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(TEST_FACULTY_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID1))
                .andExpect(jsonPath("$.name").value(NAME1))
                .andExpect(jsonPath("$.color").value(COLOR1));
    }

    @Test
    void testEditFaculty() throws Exception {
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", ID1);
        facultyObject.put("name", NAME3);
        facultyObject.put("color", COLOR3);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(TEST_FACULTY_1));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(TEST_FACULTY_3);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID1))
                .andExpect(jsonPath("$.name").value(NAME3))
                .andExpect(jsonPath("$.color").value(COLOR3));
    }

    @Test
    void testDeleteFaculty()throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(TEST_FACULTY_1));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/{id}", ID1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID1))
                .andExpect(jsonPath("$.name").value(NAME1))
                .andExpect(jsonPath("$.color").value(COLOR1));
        verify(facultyRepository,atLeastOnce()).delete(any(Faculty.class));
    }
}
