package com.kentux.alertiumtracker.interfaces;

import com.kentux.alertiumtracker.models.Alert;
import com.kentux.alertiumtracker.models.CetusCycle;
import com.kentux.alertiumtracker.models.Invasion;
import com.kentux.alertiumtracker.models.News;
import com.kentux.alertiumtracker.models.Sortie;
import com.kentux.alertiumtracker.models.SyndicateMission;
import com.kentux.alertiumtracker.models.VoidFissure;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public class RetrofitInterfaces {
    public interface NewsRetrofitInterface {
        @GET("pc/news")
        Call<ArrayList<News>> getNews();
    }

    public interface AlertsRetrofitInterface {
        @GET("pc/alerts")
        Call<ArrayList<Alert>> getAlerts();
    }

    public interface InvasionsRetrofitInterface {
        @GET("pc/invasions")
        Call<ArrayList<Invasion>> getInvasions();
    }

    public interface SyndicateMissionsRetrofitInterfaced {
        @GET("pc/syndicateMissions")
        Call<ArrayList<SyndicateMission>> getSyndicateMissions();
    }

    public interface VoidFissuresRetrofitInterface {
        @GET("pc/fissures")
        Call<ArrayList<VoidFissure>> getVoidFissures();
    }

    public interface SortiesRetrofitInterface {
        @GET("pc/sortie")
        Call<ArrayList<Sortie>> getSorties();
    }

    public interface CetusCycleRetrofitInterface {
        @GET("pc/cetysCycle")
        Call<ArrayList<CetusCycle>> getCetusCycle();
    }
}
