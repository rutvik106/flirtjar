package apimodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rutvik on 2/26/2017 at 8:37 AM.
 */

public class User
{

    /**
     * errors : {}
     * result : {"id":1,"rating":10,"language":[],"tags":[],"last_login":"2017-02-22T21:22:51.039480Z","email":"admin@gmail.com","first_name":"","last_name":"","gender":"M","dob":"2017-02-06","phone_no":"","tagline":"","looking_for":"","relationship_status":"","status":"detour","height":null,"salary":null,"country":"","hair_color":"","eye_color":"","occupation":"","drink":null,"smoking":null,"weed":null,"aquarius":"","profile_picture":"","location":{"type":"Point","coordinates":[70.990562438965,23.215141296385]},"created_date":"2017-02-06T11:20:32.378123Z","modified_date":"2017-02-26T02:55:40.244699Z","likes":1,"skipped":0,"superlikes":0,"is_instagram_activated":false,"show_me_on_jar":true,"show_me_on_nearby":true}
     */

    @SerializedName("errors")
    private ErrorsBean errors;
    @SerializedName("result")
    private ResultBean result;

    public ErrorsBean getErrors()
    {
        return errors;
    }

    public void setErrors(ErrorsBean errors)
    {
        this.errors = errors;
    }

    public ResultBean getResult()
    {
        return result;
    }

    public void setResult(ResultBean result)
    {
        this.result = result;
    }

    public static class ErrorsBean
    {
    }

    public static class ResultBean
    {
        /**
         * id : 1
         * rating : 10
         * language : []
         * tags : []
         * last_login : 2017-02-22T21:22:51.039480Z
         * email : admin@gmail.com
         * first_name :
         * last_name :
         * gender : M
         * dob : 2017-02-06
         * phone_no :
         * tagline :
         * looking_for :
         * relationship_status :
         * status : detour
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
         * location : {"type":"Point","coordinates":[70.990562438965,23.215141296385]}
         * created_date : 2017-02-06T11:20:32.378123Z
         * modified_date : 2017-02-26T02:55:40.244699Z
         * likes : 1
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
        private String lastLogin;
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
        private boolean drink;
        @SerializedName("smoking")
        private boolean smoking;
        @SerializedName("weed")
        private boolean weed;
        @SerializedName("aquarius")
        private String aquarius;
        @SerializedName("profile_picture")
        private String profilePicture;
        @SerializedName("location")
        private LocationBean location;
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

        public static void copy(ResultBean user, ResultBean userToBeUpdated)
        {
            userToBeUpdated.setEyeColor(user.getEyeColor());
            userToBeUpdated.setSalary(user.getSalary());
            userToBeUpdated.setOccupation(user.getOccupation());
            userToBeUpdated.setSmoking(user.getSmoking());
            userToBeUpdated.setWeed(user.getWeed());
            userToBeUpdated.setDrink(user.getDrink());
            userToBeUpdated.setHairColor(user.getHairColor());
            userToBeUpdated.setHeight(user.getHeight());
            userToBeUpdated.setStatus(user.getStatus());
            userToBeUpdated.setAquarius(user.getAquarius());
            userToBeUpdated.setCountry(user.getCountry());
            userToBeUpdated.setCreatedDate(user.getCreatedDate());
            userToBeUpdated.setDob(user.getDob());
            userToBeUpdated.setEmail(user.getEmail());
            userToBeUpdated.setFirstName(user.getFirstName());
            userToBeUpdated.setLastName(user.getLastName());
            userToBeUpdated.setGender(user.getGender());
            userToBeUpdated.setId(user.getId());
            userToBeUpdated.setIsInstagramActivated(user.isIsInstagramActivated());
            userToBeUpdated.setPhoneNo(user.getPhoneNo());
            userToBeUpdated.setLastLogin(user.getLastLogin());
            userToBeUpdated.setLocation(user.getLocation());
            userToBeUpdated.setLookingFor(user.getLookingFor());
            userToBeUpdated.setModifiedDate(user.getModifiedDate());
            userToBeUpdated.setLikes(user.getLikes());
            userToBeUpdated.setSuperlikes(user.getSuperlikes());
            userToBeUpdated.setSkipped(user.getSkipped());
            userToBeUpdated.setTagline(user.getTagline());
            userToBeUpdated.setShowMeOnNearby(user.isShowMeOnNearby());
            userToBeUpdated.setShowMeOnJar(user.isShowMeOnJar());
            userToBeUpdated.setRelationshipStatus(user.getRelationshipStatus());
            userToBeUpdated.setTags(user.getTags());
            userToBeUpdated.setLanguage(user.getLanguage());
            userToBeUpdated.setRating(user.getRating());
            userToBeUpdated.setProfilePicture(user.getProfilePicture());
        }

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

        public String getLastLogin()
        {
            return lastLogin;
        }

        public void setLastLogin(String lastLogin)
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
            return hairColor != null ? hairColor : "";
        }

        public void setHairColor(String hairColor)
        {
            this.hairColor = hairColor;
        }

        public String getEyeColor()
        {
            return eyeColor != null ? eyeColor : "";
        }

        public void setEyeColor(String eyeColor)
        {
            this.eyeColor = eyeColor;
        }

        public String getOccupation()
        {
            return occupation != null ? occupation : "";
        }

        public void setOccupation(String occupation)
        {
            this.occupation = occupation;
        }

        public boolean getDrink()
        {
            return drink;
        }

        public void setDrink(boolean drink)
        {
            this.drink = drink;
        }

        public boolean getSmoking()
        {
            return smoking;
        }

        public void setSmoking(boolean smoking)
        {
            this.smoking = smoking;
        }

        public boolean getWeed()
        {
            return weed;
        }

        public void setWeed(boolean weed)
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

        public LocationBean getLocation()
        {
            return location;
        }

        public void setLocation(LocationBean location)
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

        public static class LocationBean
        {
            /**
             * type : Point
             * coordinates : [70.990562438965,23.215141296385]
             */

            @SerializedName("type")
            private String type;
            @SerializedName("coordinates")
            private List<Double> coordinates;

            public String getType()
            {
                return type;
            }

            public void setType(String type)
            {
                this.type = type;
            }

            public List<Double> getCoordinates()
            {
                return coordinates;
            }

            public void setCoordinates(List<Double> coordinates)
            {
                this.coordinates = coordinates;
            }
        }
    }
}
