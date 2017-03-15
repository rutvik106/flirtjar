package api;

import apimodels.Cards;
import apimodels.CreateUser;
import apimodels.CreatedUser;
import apimodels.Gift;
import apimodels.NearByUser;
import apimodels.NotificationDeviceDetails;
import apimodels.NotificationList;
import apimodels.UpdateUser;
import apimodels.Views;
import okhttp3.ResponseBody;
import retrofit2.Call;
import utils.Constants;

/**
 * Created by rutvik on 1/22/2017 at 11:13 PM.
 */

public class API
{

    public static class User
    {
        private static ApiInterface.Users users =
                ApiClient.getClient().create(ApiInterface.Users.class);

        public static Call<CreatedUser> createUser(final CreateUser userInfo,
                                                   final RetrofitCallback<CreatedUser> callback)
        {
            Call<CreatedUser> call = users.createUser(userInfo, Constants.CONTENT_TYPE_JSON);
            call.enqueue(callback);
            return call;
        }

        public static Call<UpdateUser> updateUserDetails(final apimodels.User.ResultBean user,
                                                         final String token,
                                                         final RetrofitCallback<UpdateUser> callback)
        {
            Call<UpdateUser> call = users.updateUserDetails(user, Constants.CONTENT_TYPE_JSON,
                    token);
            call.enqueue(callback);
            return call;
        }

        public static Call<apimodels.User> getCurrentUser(final String token,
                                                          final RetrofitCallback<apimodels.User> callback)
        {
            Call<apimodels.User> call = users.getCurrentUser(token);
            call.enqueue(callback);
            return call;
        }

        public static Call<apimodels.User> getUser(final String userId,
                                                   final RetrofitCallback<apimodels.User> callback)
        {
            Call<apimodels.User> call = users.getUser(userId);
            call.enqueue(callback);
            return call;
        }

    }

    public static class Profile
    {
        private static ApiInterface.Profile profile =
                ApiClient.getClient().create(ApiInterface.Profile.class);

        public static Call<Cards> getCards(final int page,
                                           final String token,
                                           final RetrofitCallback<Cards> callback)
        {
            Call<Cards> call = profile.getCards(page, token);
            call.enqueue(callback);
            return call;
        }

        public static Call<Views> getViews(final String userId,
                                           final String token,
                                           final RetrofitCallback<Views> callback)
        {
            Call<Views> call = profile.getViews(userId, token);
            call.enqueue(callback);
            return call;
        }


    }

    public static class Location
    {

        private static ApiInterface.Location location =
                ApiClient.getClient().create(ApiInterface.Location.class);

        public static Call<NearByUser> getNearByUsers(int distance,
                                                      ApiInterface.Location.LocationUnit unit,
                                                      String token,
                                                      RetrofitCallback<NearByUser> callback)
        {
            Call<NearByUser> call = location
                    .getNearByUsers(distance,
                            unit.getValue(),
                            token);
            call.enqueue(callback);
            return call;
        }

    }

    public static class Notifications
    {
        private static ApiInterface.Notifications notifications =
                ApiClient.getClient().create(ApiInterface.Notifications.class);


        public static Call<ResponseBody> registerNotificationDevice(NotificationDeviceDetails deviceDetails,
                                                                    String token,
                                                                    RetrofitCallback<ResponseBody> callback)
        {
            Call<ResponseBody> call = notifications.registerNotificationDevice(deviceDetails,
                    Constants.CONTENT_TYPE_JSON,
                    token);

            call.enqueue(callback);

            return call;
        }

        public static Call<NotificationList> getNotifications(int pageNo,
                                                              String token,
                                                              RetrofitCallback<NotificationList> callback)
        {
            Call<NotificationList> call = notifications.getNotifications(pageNo,
                    token);

            call.enqueue(callback);

            return call;
        }


    }

    public static class Gifts
    {
        public static ApiInterface.Gifts gifts = ApiClient.getClient()
                .create(ApiInterface.Gifts.class);

        public static Call<Gift> getGifts(int page, String token, RetrofitCallback<Gift> callback)
        {
            Call<Gift> call = gifts.getGifts(page, token);

            call.enqueue(callback);

            return call;
        }

    }

}
