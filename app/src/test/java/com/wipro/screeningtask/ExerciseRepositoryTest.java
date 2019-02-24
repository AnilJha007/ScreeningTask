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
import com.wipro.screeningtask.utils.SchedulerProvider.BaseSchedulerProvider;
import com.wipro.screeningtask.utils.SchedulerProvider.SchedulerProviderTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Calls;

import java.util.ArrayList;

import io.reactivex.Observable;


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

        exerciseRepository = Mockito.spy(new ExerciseRepository(application, internetUtil, exerciseDatabase, apiInterface, new SchedulerProviderTest()));
    }


    @Test
    public void internetError() {

        Mockito.doReturn(false).when(internetUtil).isNetworkAvailable();

        Resources resources = Mockito.mock(Resources.class);

        Mockito.doReturn(resources).when(application).getResources();

        Mockito.doReturn("no internet error").when(resources).getString(R.string.internet_not_available);
    }

    @Test
    public void getErrorDataForExerciseListApi() {

        Mockito.doReturn(true).when(internetUtil).isNetworkAvailable();

        ExerciseDao exerciseDao = Mockito.mock(ExerciseDao.class);
        Mockito.doReturn(exerciseDao).when(exerciseDatabase).exerciseDao();

        Mockito.doReturn(0L).when(exerciseDao).getExerciseCount();

        Mockito.doReturn(Observable.error(new Exception())).when(apiInterface).getExerciseList();

        Resources resources = Mockito.mock(Resources.class);

        Mockito.doReturn(resources).when(application).getResources();

        Mockito.doReturn("error").when(resources).getString(R.string.error_try_later);

        exerciseRepository.getExerciseList(true);

    }

    @Test
    public void getSuccessfulDataForExerciseListApi() {

        Mockito.doReturn(true).when(internetUtil).isNetworkAvailable();

        ExerciseList exerciseList = Mockito.mock(ExerciseList.class);

        ArrayList<ExerciseEntity> mockExerciseList = Mockito.mock(ArrayList.class);

        ExerciseDao exerciseDao = Mockito.mock(ExerciseDao.class);

        Mockito.doReturn("toolbar title").when(exerciseList).getTitle();
        Mockito.doReturn(mockExerciseList).when(exerciseList).getRows();
        Mockito.doReturn(exerciseDao).when(exerciseDatabase).exerciseDao();
        Mockito.doReturn(Observable.just(exerciseList)).when(apiInterface).getExerciseList();

        exerciseRepository.getExerciseList(true);
    }

}


