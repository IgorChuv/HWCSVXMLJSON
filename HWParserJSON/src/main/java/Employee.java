public class Employee {
    public long id;
    public String firstName;
    public String lastName;
    public String country;
    public int age;

    public Employee() {

    }

    public void setParsedData(String Key, String Value) {
        if(Key.equals("id")){
            this.id = Integer.parseInt(Value);
        }else if(Key.equals("firstName")){
            this.firstName = Value;
        }else if(Key.equals("lastName")){
            this.lastName = Value;
        }else if(Key.equals("country")){
            this.country = Value;
        }else if(Key.equals("age")){
            this.age = Integer.parseInt(Value);
        }
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Employee(long id, String firstName, String lastName, String country, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;
    }

    @Override
    public String toString() {
        return "id: " + id + " | Name: " + firstName + " | Lastname: " + lastName + " | Country: " + country + " | Age: " + age;
    }
}