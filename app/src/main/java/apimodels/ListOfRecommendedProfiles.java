package apimodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rutvik on 3/25/2017 at 2:00 PM.
 */

public class ListOfRecommendedProfiles
{

    /**
     * errors : {}
     * result : [{"id":5,"rating":5,"language":[],"tags":[],"last_login":null,"email":"rutvik@gmail.com","first_name":"Rutvik","last_name":"","gender":"","dob":null,"phone_no":"","tagline":"","looking_for":"","relationship_status":"","status":"","height":null,"salary":null,"country":"","hair_color":"","eye_color":"","occupation":"","drink":null,"smoking":null,"weed":null,"aquarius":"","profile_picture":"","location":null,"created_date":"2017-02-06T12:19:29.564398Z","modified_date":"2017-02-06T12:19:29.564430Z","likes":0,"skipped":0,"superlikes":0,"is_instagram_activated":false,"show_me_on_jar":true,"show_me_on_nearby":true},{"id":6,"rating":3.14,"language":["Hindi","English"],"tags":null,"last_login":null,"email":"iosteam2016@gmail.com","first_name":"Narendra","last_name":"Suthar","gender":"","dob":null,"phone_no":"","tagline":"","looking_for":"","relationship_status":"","status":"","height":null,"salary":null,"country":"","hair_color":"","eye_color":"","occupation":"","drink":null,"smoking":null,"weed":null,"aquarius":"","profile_picture":"https://scontent.xx.fbcdn.net/v/t1.0-1/p200x200/15420854_356748618033308_1961839182452950093_n.jpg?oh=e280b40bf05cef53a4eb346a27b10bbe&oe=590B5F9C","location":{"type":"Point","coordinates":[19.0176147,72.8561644]},"created_date":"2017-02-06T16:36:38.664094Z","modified_date":"2017-03-05T10:06:32.365674Z","likes":8,"skipped":24,"superlikes":3,"is_instagram_activated":false,"show_me_on_jar":true,"show_me_on_nearby":true},{"id":1,"rating":10,"language":[],"tags":[],"last_login":"2017-03-23T18:08:00.019119Z","email":"admin@gmail.com","first_name":"","last_name":"","gender":"M","dob":"2017-02-06","phone_no":"","tagline":"","looking_for":"","relationship_status":"","status":"detour","height":null,"salary":null,"country":"","hair_color":"","eye_color":"","occupation":"","drink":null,"smoking":null,"weed":null,"aquarius":"","profile_picture":"","location":{"type":"Point","coordinates":[70.990562438965,23.215141296385]},"created_date":"2017-02-06T11:20:32.378123Z","modified_date":"2017-02-26T02:55:40.244699Z","likes":1,"skipped":0,"superlikes":0,"is_instagram_activated":false,"show_me_on_jar":true,"show_me_on_nearby":true},{"id":23,"rating":9.69,"language":[],"tags":[],"last_login":"2017-03-08T09:37:36Z","email":"manisha@gmail.com","first_name":"Manisha","last_name":"Singh","gender":"F","dob":"2000-05-08","phone_no":"","tagline":"","looking_for":"F","relationship_status":"","status":"movie","height":5.7,"salary":594934,"country":"India","hair_color":"br","eye_color":"bu","occupation":"Manager","drink":false,"smoking":true,"weed":true,"aquarius":"cp","profile_picture":"https://i.mdel.net/i/db/2015/5/387209/387209-500w.jpg","location":{"type":"Point","coordinates":[71.103515625,22.65380859375]},"created_date":"2017-03-08T09:39:25.725934Z","modified_date":"2017-03-24T17:19:09.820937Z","likes":1603,"skipped":66,"superlikes":492,"is_instagram_activated":false,"show_me_on_jar":true,"show_me_on_nearby":true},{"id":8,"rating":2.5,"language":[],"tags":[],"last_login":null,"email":"girl2@gmail.com","first_name":"","last_name":"","gender":"F","dob":"1995-12-18","phone_no":"","tagline":"","looking_for":"","relationship_status":"","status":"","height":null,"salary":null,"country":"","hair_color":"","eye_color":"","occupation":"","drink":null,"smoking":null,"weed":null,"aquarius":"","profile_picture":"","location":{"type":"Point","coordinates":[70.576171875,22.7197265625]},"created_date":"2017-02-06T21:02:52.714033Z","modified_date":"2017-03-23T06:30:38.027135Z","likes":10,"skipped":30,"superlikes":0,"is_instagram_activated":false,"show_me_on_jar":true,"show_me_on_nearby":true},{"id":28,"rating":10,"language":["English","Hindi","Marathi"],"tags":["Cool","HOT"],"last_login":"2017-03-08T09:53:01Z","email":"kriti@gmail.com","first_name":"Kriti","last_name":"","gender":"F","dob":"1991-04-05","phone_no":"","tagline":"Cool","looking_for":"M","relationship_status":"S","status":"detour","height":null,"salary":23424324,"country":"India","hair_color":"bl","eye_color":"br","occupation":"Model","drink":true,"smoking":false,"weed":false,"aquarius":"ta","profile_picture":"http://theweddingtiara.com/wp-content/uploads/2013/04/Bianca-Balti-Fashion-Model-Profile-on-New-York-Magazine.jpg","location":{"type":"Point","coordinates":[70.691528320313,22.670288085938]},"created_date":"2017-03-08T09:55:08.100044Z","modified_date":"2017-03-23T06:30:38.009581Z","likes":10032424,"skipped":256,"superlikes":21313,"is_instagram_activated":false,"show_me_on_jar":true,"show_me_on_nearby":true}]
     */

    @SerializedName("errors")
    private ErrorsBean errors;
    @SerializedName("result")
    private List<ResultBean> result;

    public ErrorsBean getErrors()
    {
        return errors;
    }

    public void setErrors(ErrorsBean errors)
    {
        this.errors = errors;
    }

    public List<ResultBean> getResult()
    {
        return result;
    }

    public void setResult(List<ResultBean> result)
    {
        this.result = result;
    }

    public static class ErrorsBean
    {
    }

    public static class ResultBean
    {
        /**
         * id : 5
         * rating : 5
         * language : []
         * tags : []
         * last_login : null
         * email : rutvik@gmail.com
         * first_name : Rutvik
         * last_name :
         * gender :
         * dob : null
         * phone_no :
         * tagline :
         * looking_for :
         * relationship_status :
         * status :
         * height : null
         * salary : null
         * country :
         * hair_color :
         * eye_color :
         * occupation :
         * drink : null
         * smoking : null
         * weed : null
         * aquarius :
         * profile_picture :
         * location : null
         * created_date : 2017-02-06T12:19:29.564398Z
         * modified_date : 2017-02-06T12:19:29.564430Z
         * likes : 0
         * skipped : 0
         * superlikes : 0
         * is_instagram_activated : false
         * show_me_on_jar : true
         * show_me_on_nearby : true
         */

        @SerializedName("id")
        private int id;
        @SerializedName("rating")
        private int rating;
        @SerializedName("last_login")
        private Object lastLogin;
        @SerializedName("email")
        private String email;
        @SerializedName("first_name")
        private String firstName;
        @SerializedName("last_name")
        private String lastName;
        @SerializedName("gender")
        private String gender;
        @SerializedName("dob")
        private String dob;
        @SerializedName("phone_no")
        private String phoneNo;
        @SerializedName("tagline")
        private String tagline;
        @SerializedName("looking_for")
        private String lookingFor;
        @SerializedName("relationship_status")
        private String relationshipStatus;
        @SerializedName("status")
        private String status;
        @SerializedName("height")
        private double height;
        @SerializedName("salary")
        private int salary;
        @SerializedName("country")
        private String country;
        @SerializedName("hair_color")
        private String hairColor;
        @SerializedName("eye_color")
        private String eyeColor;
        @SerializedName("occupation")
        private String occupation;
        @SerializedName("drink")
        private Object drink;
        @SerializedName("smoking")
        private Object smoking;
        @SerializedName("weed")
        private Object weed;
        @SerializedName("aquarius")
        private String aquarius;
        @SerializedName("profile_picture")
        private String profilePicture;
        @SerializedName("location")
        private Object location;
        @SerializedName("created_date")
        private String createdDate;
        @SerializedName("modified_date")
        private String modifiedDate;
        @SerializedName("likes")
        private int likes;
        @SerializedName("skipped")
        private int skipped;
        @SerializedName("superlikes")
        private int superlikes;
        @SerializedName("is_instagram_activated")
        private boolean isInstagramActivated;
        @SerializedName("show_me_on_jar")
        private boolean showMeOnJar;
        @SerializedName("show_me_on_nearby")
        private boolean showMeOnNearby;
        @SerializedName("language")
        private List<?> language;
        @SerializedName("tags")
        private List<?> tags;

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public int getRating()
        {
            return rating;
        }

        public void setRating(int rating)
        {
            this.rating = rating;
        }

        public Object getLastLogin()
        {
            return lastLogin;
        }

        public void setLastLogin(Object lastLogin)
        {
            this.lastLogin = lastLogin;
        }

        public String getEmail()
        {
            return email;
        }

        public void setEmail(String email)
        {
            this.email = email;
        }

        public String getFirstName()
        {
            return firstName;
        }

        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }

        public String getLastName()
        {
            return lastName;
        }

        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }

        public String getGender()
        {
            return gender;
        }

        public void setGender(String gender)
        {
            this.gender = gender;
        }

        public String getDob()
        {
            return dob;
        }

        public void setDob(String dob)
        {
            this.dob = dob;
        }

        public String getPhoneNo()
        {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo)
        {
            this.phoneNo = phoneNo;
        }

        public String getTagline()
        {
            return tagline;
        }

        public void setTagline(String tagline)
        {
            this.tagline = tagline;
        }

        public String getLookingFor()
        {
            return lookingFor;
        }

        public void setLookingFor(String lookingFor)
        {
            this.lookingFor = lookingFor;
        }

        public String getRelationshipStatus()
        {
            return relationshipStatus;
        }

        public void setRelationshipStatus(String relationshipStatus)
        {
            this.relationshipStatus = relationshipStatus;
        }

        public String getStatus()
        {
            return status;
        }

        public void setStatus(String status)
        {
            this.status = status;
        }

        public double getHeight()
        {
            return height;
        }

        public void setHeight(double height)
        {
            this.height = height;
        }

        public int getSalary()
        {
            return salary;
        }

        public void setSalary(int salary)
        {
            this.salary = salary;
        }

        public String getCountry()
        {
            return country;
        }

        public void setCountry(String country)
        {
            this.country = country;
        }

        public String getHairColor()
        {
            return hairColor;
        }

        public void setHairColor(String hairColor)
        {
            this.hairColor = hairColor;
        }

        public String getEyeColor()
        {
            return eyeColor;
        }

        public void setEyeColor(String eyeColor)
        {
            this.eyeColor = eyeColor;
        }

        public String getOccupation()
        {
            return occupation;
        }

        public void setOccupation(String occupation)
        {
            this.occupation = occupation;
        }

        public Object getDrink()
        {
            return drink;
        }

        public void setDrink(Object drink)
        {
            this.drink = drink;
        }

        public Object getSmoking()
        {
            return smoking;
        }

        public void setSmoking(Object smoking)
        {
            this.smoking = smoking;
        }

        public Object getWeed()
        {
            return weed;
        }

        public void setWeed(Object weed)
        {
            this.weed = weed;
        }

        public String getAquarius()
        {
            return aquarius;
        }

        public void setAquarius(String aquarius)
        {
            this.aquarius = aquarius;
        }

        public String getProfilePicture()
        {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture)
        {
            this.profilePicture = profilePicture;
        }

        public Object getLocation()
        {
            return location;
        }

        public void setLocation(Object location)
        {
            this.location = location;
        }

        public String getCreatedDate()
        {
            return createdDate;
        }

        public void setCreatedDate(String createdDate)
        {
            this.createdDate = createdDate;
        }

        public String getModifiedDate()
        {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate)
        {
            this.modifiedDate = modifiedDate;
        }

        public int getLikes()
        {
            return likes;
        }

        public void setLikes(int likes)
        {
            this.likes = likes;
        }

        public int getSkipped()
        {
            return skipped;
        }

        public void setSkipped(int skipped)
        {
            this.skipped = skipped;
        }

        public int getSuperlikes()
        {
            return superlikes;
        }

        public void setSuperlikes(int superlikes)
        {
            this.superlikes = superlikes;
        }

        public boolean isIsInstagramActivated()
        {
            return isInstagramActivated;
        }

        public void setIsInstagramActivated(boolean isInstagramActivated)
        {
            this.isInstagramActivated = isInstagramActivated;
        }

        public boolean isShowMeOnJar()
        {
            return showMeOnJar;
        }

        public void setShowMeOnJar(boolean showMeOnJar)
        {
            this.showMeOnJar = showMeOnJar;
        }

        public boolean isShowMeOnNearby()
        {
            return showMeOnNearby;
        }

        public void setShowMeOnNearby(boolean showMeOnNearby)
        {
            this.showMeOnNearby = showMeOnNearby;
        }

        public List<?> getLanguage()
        {
            return language;
        }

        public void setLanguage(List<?> language)
        {
            this.language = language;
        }

        public List<?> getTags()
        {
            return tags;
        }

        public void setTags(List<?> tags)
        {
            this.tags = tags;
        }
    }
}
