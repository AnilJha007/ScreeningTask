package com.wipro.screeningtask;

import android.app.Application;
import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.content.res.Resources;

import com.wipro.screeningtask.database.ExerciseDatabase;
import com.wipro.screeningtask.database.dao.ExerciseDao;
import com.wipro.screeningtask.database.entity.ExerciseEntity;
import com.wipro.screeningtask.exercise.pojo.ExerciseList;
import com.wipro.screeningtask.exercise.ui.ExerciseRepository;
import com.wipro.screeningtask.network.ApiInterface;
import com.wipro.screeningtask.utils.InternetUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import okhttp3.Call;

public class ExerciseRepositoryTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    private Application application;
    @Mock
    private ExerciseDatabase exerciseDatabase;
    @Mock
    private InternetUtil internetUtil;
    @Mock
    private ExerciseRepository exerciseRepository;
    @Mock
    private ApiInterface apiInterface;


    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        exerciseRepository = Mockito.spy(new ExerciseRepository(application, internetUtil, exerciseDatabase, apiInterface));

    }


    @Test
    public void internetError() {

        Mockito.doReturn(false).when(internetUtil).isNetworkAvailable();

        Resources resources = Mockito.mock(Resources.class);

        Mockito.doReturn(resources).when(application).getResources();

        Mockito.doReturn("no internet error").when(resources).getString(R.string.internet_not_available);
    }

    @Test
    public void apiResponseError() {

        Mockito.doReturn(true).when(internetUtil).isNetworkAvailable();

    }

    @Test
    public void successfulData() {

        Mockito.doReturn(true).when(internetUtil).isNetworkAvailable();
    }

}


