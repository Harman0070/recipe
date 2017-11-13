package com.example.harmandeepsingh.jsonretro.Interface;


import com.example.harmandeepsingh.jsonretro.models.AddcategoryModel;
import com.example.harmandeepsingh.jsonretro.models.Addcountrysearch2Model;
import com.example.harmandeepsingh.jsonretro.models.CountryrecipeModel;
import com.example.harmandeepsingh.jsonretro.models.Ingre1Model;
import com.example.harmandeepsingh.jsonretro.models.LikeModel;
import com.example.harmandeepsingh.jsonretro.models.MyrecipesModel;
import com.example.harmandeepsingh.jsonretro.models.NewestModel;
import com.example.harmandeepsingh.jsonretro.models.Noofrowsmodel;
import com.example.harmandeepsingh.jsonretro.models.RecipeModel;
import com.example.harmandeepsingh.jsonretro.models.RecipePartModel;
import com.example.harmandeepsingh.jsonretro.models.StepsprepareModel;
import com.example.harmandeepsingh.jsonretro.models.SubjectsDetails;
import com.example.harmandeepsingh.jsonretro.models.TimeModel;
import com.example.harmandeepsingh.jsonretro.models.UserLogin;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Harmandeep singh on 8/10/2017.
 */

public interface APIService {
    @FormUrlEncoded
    @POST("signup.php")
    Call<SubjectsDetails> getUserDetails(@Field("fname") String Firstname, @Field("lname") String lastname,@Field("email") String email,@Field("password") String Password);

    @FormUrlEncoded
    @POST("login.php")
    Call<UserLogin> getLoginDetsils(@Field("email") String Email, @Field("password") String Password);

    @FormUrlEncoded
    @POST("recipecategorytbl.php")
    Call<RecipeModel> getcategoryrecipes(@Field("category_parent_id") int id);

    @FormUrlEncoded
    @POST("particularrecipetbl.php")
    Call<RecipePartModel> getparticularrecipes(@Field("pcategory_id") String parent_id,@Field("offset") int offset,@Field("limit") int limit);

    @FormUrlEncoded
    @POST("ingredients.php")
    Call<Ingre1Model> getingredients(@Field("precipe_id") String precipe_id);

    @FormUrlEncoded
    @POST("addrecipe2_delete.php")
    Call<Ingre1Model> getingredientid(@Field("ing_id") String id);

    @FormUrlEncoded
    @POST("steps.php")
    Call<StepsprepareModel> getsteps(@Field("precipe_id") String precipe_id);

    @FormUrlEncoded
    @POST("recipenavigationtbl.php")
    Call<CountryrecipeModel> getcountryid(@Field("") String country_type);

    @FormUrlEncoded
    @POST("newesttbl.php")
    Call<NewestModel> getnewestrecipeid(@Field("limit") int limit,@Field("offset") int offset);

    @FormUrlEncoded
    @POST("timetbl.php")
    Call<TimeModel> getpreptime(@Field("precipe_id") String precipe_id);

    @FormUrlEncoded
    @POST("like.php")
    Call<LikeModel> getlikesid(@Field("userid") String userid, @Field("recipe_id") String recipe_id, @Field("like") String like, @Field("checked") Boolean checked);

    @FormUrlEncoded
    @POST("getlike.php")
    Call<Noofrowsmodel> getnooflikes(@Field("userid") String userid,@Field("recipe_id") String recipe_id);

    @FormUrlEncoded
    @POST("likecount.php")
    Call<Noofrowsmodel> getnooflikesall(@Field("recipe_id") String recipe_id);

    @FormUrlEncoded
    @POST("add_select_category.php")
    Call<AddcategoryModel> parentid(@Field("category_parent_id") String countryid);

    @FormUrlEncoded
    @POST("add_search2_country.php")
    Call<Addcountrysearch2Model> countryid(@Field("country_name") String country_name,@Field("category_name") String category_name);

    @FormUrlEncoded
    @POST("add_search3_category.php")
    Call<Addcountrysearch2Model> getcountryid(@Field("category_name") String category_name,@Field("country_type") String countryid);

    @FormUrlEncoded
    @POST("addrecipe1.php")
    Call<Addcountrysearch2Model> addData(@Field("precipe_parent_id") String cat_id,@Field("user_id") String user_id,@Field("precipe_name") String getname,@Field("precipe_detail") String getdetail,@Field("precipe_video_id") String getvideoid,@Field("time") String gettime,@Field("servings") String getservings);

    //fileToUpload,Ruser_id,RName,RDetail,RVideoID,RTime,RServing,RCatID
    @Multipart
    @POST("addrecipe1.php")
    Call<Addcountrysearch2Model> uploadimage(@Part MultipartBody.Part file, @Part("user_id") RequestBody Ruserid, @Part("precipe_name") RequestBody RName, @Part("precipe_detail") RequestBody RDetail, @Part("precipe_video_id") RequestBody RVideoID, @Part("time") RequestBody RTime, @Part("servings") RequestBody RServing, @Part("precipe_parent_id") RequestBody RCatID);

    @FormUrlEncoded
    @POST("addrecipe2.php")
    Call<Addcountrysearch2Model> addrecipe2data(@Field("ing_parent_id") String cat_id,@Field("ing_detail") String ingredient,@Field("ing_amt") String amount);

    @FormUrlEncoded
    @POST("addrecipe3.php")
    Call<Addcountrysearch2Model> addrecipe3data(@Field("step_parent_id") String step_parent_id,@Field("step_detail") String steps);

    @FormUrlEncoded
    @POST("myrecipes.php")
    Call<MyrecipesModel> getmyrecipes(@Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("search_recipe.php")
    Call<NewestModel> searchrecipe(@Field("recipename") String recipename,@Field ("limit")int limit);
}

