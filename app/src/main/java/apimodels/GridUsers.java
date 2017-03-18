package apimodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rutvik on 3/18/2017 at 3:50 PM.
 */

public class GridUsers
{


    /**
     * errors : {}
     * result : [{"id":1,"email":"admin@gmail.com","first_name":"","last_name":"","profile_picture":"","location":{"type":"Point","coordinates":[70.990562438965,23.215141296385]},"status":"detour","gender":"M"}]
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
         * id : 1
         * email : admin@gmail.com
         * first_name :
         * last_name :
         * profile_picture :
         * location : {"type":"Point","coordinates":[70.990562438965,23.215141296385]}
         * status : detour
         * gender : M
         */

        @SerializedName("id")
        private int id;
        @SerializedName("email")
        private String email;
        @SerializedName("first_name")
        private String firstName;
        @SerializedName("last_name")
        private String lastName;
        @SerializedName("profile_picture")
        private String profilePicture;
        @SerializedName("location")
        private LocationBean location;
        @SerializedName("status")
        private String status;
        @SerializedName("gender")
        private String gender;

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
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

        public String getStatus()
        {
            return status;
        }

        public void setStatus(String status)
        {
            this.status = status;
        }

        public String getGender()
        {
            return gender;
        }

        public void setGender(String gender)
        {
            this.gender = gender;
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
