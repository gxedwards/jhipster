package com.demo.web.rest;

import com.demo.Application;
import com.demo.domain.Study;
import com.demo.repository.StudyRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StudyResource REST controller.
 *
 * @see StudyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StudyResourceTest {

    private static final String DEFAULT_STUDY_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_STUDY_NAME = "UPDATED_TEXT";

    @Inject
    private StudyRepository studyRepository;

    private MockMvc restStudyMockMvc;

    private Study study;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StudyResource studyResource = new StudyResource();
        ReflectionTestUtils.setField(studyResource, "studyRepository", studyRepository);
        this.restStudyMockMvc = MockMvcBuilders.standaloneSetup(studyResource).build();
    }

    @Before
    public void initTest() {
        study = new Study();
        study.setStudy_name(DEFAULT_STUDY_NAME);
    }

    @Test
    @Transactional
    public void createStudy() throws Exception {
        // Validate the database is empty
        assertThat(studyRepository.findAll()).hasSize(0);

        // Create the Study
        restStudyMockMvc.perform(post("/api/studys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(study)))
                .andExpect(status().isCreated());

        // Validate the Study in the database
        List<Study> studys = studyRepository.findAll();
        assertThat(studys).hasSize(1);
        Study testStudy = studys.iterator().next();
        assertThat(testStudy.getStudy_name()).isEqualTo(DEFAULT_STUDY_NAME);
    }

    @Test
    @Transactional
    public void getAllStudys() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        // Get all the studys
        restStudyMockMvc.perform(get("/api/studys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(study.getId().intValue()))
                .andExpect(jsonPath("$.[0].study_name").value(DEFAULT_STUDY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getStudy() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        // Get the study
        restStudyMockMvc.perform(get("/api/studys/{id}", study.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(study.getId().intValue()))
            .andExpect(jsonPath("$.study_name").value(DEFAULT_STUDY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudy() throws Exception {
        // Get the study
        restStudyMockMvc.perform(get("/api/studys/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudy() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        // Update the study
        study.setStudy_name(UPDATED_STUDY_NAME);
        restStudyMockMvc.perform(put("/api/studys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(study)))
                .andExpect(status().isOk());

        // Validate the Study in the database
        List<Study> studys = studyRepository.findAll();
        assertThat(studys).hasSize(1);
        Study testStudy = studys.iterator().next();
        assertThat(testStudy.getStudy_name()).isEqualTo(UPDATED_STUDY_NAME);
    }

    @Test
    @Transactional
    public void deleteStudy() throws Exception {
        // Initialize the database
        studyRepository.saveAndFlush(study);

        // Get the study
        restStudyMockMvc.perform(delete("/api/studys/{id}", study.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Study> studys = studyRepository.findAll();
        assertThat(studys).hasSize(0);
    }
}
