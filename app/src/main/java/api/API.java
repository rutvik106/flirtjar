package api;

import java.util.List;

import apimodels.Cards;
import apimodels.ChatMessages;
import apimodels.CreateUser;
import apimodels.CreatedUser;
import apimodels.Gift;
import apimodels.GridUsers;
import apimodels.MatchedProfiles;
import apimodels.NearByUser;
import apimodels.NotificationDeviceDetails;
import apimodels.NotificationList;
import apimodels.OnAddPicturesResponse;
import apimodels.OtherPictures;
import apimodels.Picture;
import apimodels.SendChatMessage;
import apimodels.SentMessage;
import apimodels.UpdateUser;
import apimodels.Views;
import instagram.InstaConstants;
import instagram.apimodels.AuthorizedUser;
import instagram.apimodels.RecentMedia;
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

        public static Call<GridUsers> getViewUsers(final int userId,
                                                   final ApiInterface.Profile.ResponseType response,
                                                   final String token,
                                                   final RetrofitCallback<GridUsers> callback)
        {
            Call<GridUsers> call = profile.getViewUsers(userId, response.getValue(), token);
            call.enqueue(callback);
            return call;
        }

        public static Call<MatchedProfiles> getMatchedProfiles(final int page,
                                                               final String token,
                                                               final RetrofitCallback<MatchedProfiles> callback)
        {
            Call<MatchedProfiles> call = profile.getMatchedProfiles(page, token);
            call.enqueue(callback);
            return call;
        }

        public static Call<OtherPictures> getOtherPictures(final int pageNo,
                                                           final String token,
                                                           final RetrofitCallback<OtherPictures> callback)
        {
            Call<OtherPictures> call = profile.getOtherPictures(pageNo,
                    token);
            call.enqueue(callback);
            return call;
        }

        public static Call<OnAddPicturesResponse> addPictures(final List<Picture> pictures,
                                                              final String token,
                                                              final RetrofitCallback<OnAddPicturesResponse> callback)
        {
            Call<OnAddPicturesResponse> call = profile.addPictures(pictures, Constants.CONTENT_TYPE_JSON,
                    token);
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

    public static class Chat
    {
        public static ApiInterface.Chat chat = ApiClient.getClient()
                .create(ApiInterface.Chat.class);

        public static Call<ChatMessages> getChatMessages(final int fromUserId,
                                                         final String token,
                                                         final RetrofitCallback<ChatMessages> callback)
        {
            Call<ChatMessages> call = chat.getChatMessages(fromUserId, token);

            call.enqueue(callback);

            return call;
        }

        public static Call<SentMessage> sendChatMessage(final SendChatMessage message,
                                                        final String token,
                                                        final RetrofitCallback<SentMessage> callback)
        {
            Call<SentMessage> call = chat.sendChatMessage(message, token);

            call.enqueue(callback);

            return call;
        }

    }

    public static class Instagram
    {
        public static ApiInterface.Instagram instagram = ApiClient.getClientForInsta()
                .create(ApiInterface.Instagram.class);

        public static Call<AuthorizedUser> getAccessToken(final String requestToken,
                                                          final RetrofitCallback<AuthorizedUser> callback)
        {
            Call<AuthorizedUser> call = instagram.getAccessToken(InstaConstants.CLIENT_ID,
                    InstaConstants.CLIENT_SECRET, InstaConstants.GRANT_TYPE,
                    InstaConstants.CALLBACKURL, requestToken);

            call.enqueue(callback);

            return call;
        }

        public static Call<RecentMedia> getRecentMedia(final String accessToken,
                                                       final RetrofitCallback<RecentMedia> callback)
        {
            Call<RecentMedia> call = instagram.getRecentMedia(accessToken);

            call.enqueue(callback);

            return call;
        }

    }

}
