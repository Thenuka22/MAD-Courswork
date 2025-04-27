package com.example.courseregistration.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DatabaseHelper class that manages database creation, upgrades, and all CRUD operations
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "CourseRegistration.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_BRANCHES = "Branches";
    private static final String TABLE_COURSES = "Courses";
    private static final String TABLE_COURSE_OFFERINGS = "CourseOfferings";
    private static final String TABLE_PROMOTION_CODES = "PromotionCodes";
    private static final String TABLE_REGISTRATIONS = "Registrations";
    private static final String TABLE_NOTIFICATIONS = "Notifications";

    // Common column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CREATED_AT = "created_at";

    // Users table columns
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
    private static final String COLUMN_NIC = "nic";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_PROFILE_PICTURE = "profile_picture";
    private static final String COLUMN_VERIFICATION_CODE = "verification_code";
    private static final String COLUMN_IS_VERIFIED = "is_verified";
    private static final String COLUMN_USER_TYPE = "user_type";

    // Branches table columns
    private static final String COLUMN_BRANCH_ID = "branch_id";
    private static final String COLUMN_BRANCH_CODE = "branch_code";
    private static final String COLUMN_BRANCH_NAME = "branch_name";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";

    // Courses table columns
    private static final String COLUMN_COURSE_ID = "course_id";
    private static final String COLUMN_COURSE_NAME = "course_name";
    private static final String COLUMN_COURSE_FEE = "course_fee";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_MAX_PARTICIPANTS = "max_participants";
    private static final String COLUMN_PUBLISHED_DATE = "published_date";

    // Course Offerings table columns
    private static final String COLUMN_OFFERING_ID = "offering_id";
    private static final String COLUMN_START_DATE = "start_date";
    private static final String COLUMN_REGISTRATION_DEADLINE = "registration_deadline";
    private static final String COLUMN_IS_ACTIVE = "is_active";

    // Promotion Codes table columns
    private static final String COLUMN_PROMO_ID = "promo_id";
    private static final String COLUMN_PROMO_CODE = "promo_code";
    private static final String COLUMN_DISCOUNT_PERCENTAGE = "discount_percentage";
    private static final String COLUMN_DESCRIPTION = "description";

    // Registrations table columns
    private static final String COLUMN_REGISTRATION_ID = "registration_id";
    private static final String COLUMN_AMOUNT_PAID = "amount_paid";
    private static final String COLUMN_DISCOUNT_AMOUNT = "discount_amount";
    private static final String COLUMN_REGISTRATION_DATE = "registration_date";
    private static final String COLUMN_IS_CONFIRMED = "is_confirmed";
    private static final String COLUMN_CONFIRMATION_EMAIL_SENT = "confirmation_email_sent";

    // Notifications table columns
    private static final String COLUMN_NOTIFICATION_ID = "notification_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_MESSAGE = "message";
    private static final String COLUMN_IS_READ = "is_read";

    // Create table statements
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT NOT NULL,"
            + COLUMN_ADDRESS + " TEXT NOT NULL,"
            + COLUMN_CITY + " TEXT NOT NULL,"
            + COLUMN_DATE_OF_BIRTH + " DATE NOT NULL,"
            + COLUMN_NIC + " TEXT UNIQUE NOT NULL,"
            + COLUMN_EMAIL + " TEXT UNIQUE NOT NULL,"
            + COLUMN_GENDER + " TEXT NOT NULL,"
            + COLUMN_PHONE + " TEXT NOT NULL,"
            + COLUMN_PROFILE_PICTURE + " BLOB,"
            + COLUMN_VERIFICATION_CODE + " TEXT,"
            + COLUMN_IS_VERIFIED + " INTEGER DEFAULT 0,"
            + COLUMN_USER_TYPE + " TEXT NOT NULL,"
            + COLUMN_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

    private static final String CREATE_TABLE_BRANCHES = "CREATE TABLE " + TABLE_BRANCHES + "("
            + COLUMN_BRANCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_BRANCH_CODE + " TEXT UNIQUE NOT NULL,"
            + COLUMN_BRANCH_NAME + " TEXT NOT NULL,"
            + COLUMN_LOCATION + " TEXT NOT NULL,"
            + COLUMN_LATITUDE + " REAL,"
            + COLUMN_LONGITUDE + " REAL)";

    private static final String CREATE_TABLE_COURSES = "CREATE TABLE " + TABLE_COURSES + "("
            + COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_COURSE_NAME + " TEXT NOT NULL,"
            + COLUMN_COURSE_FEE + " REAL NOT NULL,"
            + COLUMN_DURATION + " INTEGER NOT NULL,"
            + COLUMN_MAX_PARTICIPANTS + " INTEGER NOT NULL,"
            + COLUMN_PUBLISHED_DATE + " DATE NOT NULL,"
            + COLUMN_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

    private static final String CREATE_TABLE_COURSE_OFFERINGS = "CREATE TABLE " + TABLE_COURSE_OFFERINGS + "("
            + COLUMN_OFFERING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_COURSE_ID + " INTEGER NOT NULL,"
            + COLUMN_BRANCH_ID + " INTEGER NOT NULL,"
            + COLUMN_START_DATE + " DATE NOT NULL,"
            + COLUMN_REGISTRATION_DEADLINE + " DATE NOT NULL,"
            + COLUMN_IS_ACTIVE + " INTEGER DEFAULT 1,"
            + "FOREIGN KEY (" + COLUMN_COURSE_ID + ") REFERENCES " + TABLE_COURSES + "(" + COLUMN_COURSE_ID + "),"
            + "FOREIGN KEY (" + COLUMN_BRANCH_ID + ") REFERENCES " + TABLE_BRANCHES + "(" + COLUMN_BRANCH_ID + "))";

    private static final String CREATE_TABLE_PROMOTION_CODES = "CREATE TABLE " + TABLE_PROMOTION_CODES + "("
            + COLUMN_PROMO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PROMO_CODE + " TEXT UNIQUE NOT NULL,"
            + COLUMN_DISCOUNT_PERCENTAGE + " INTEGER NOT NULL,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_IS_ACTIVE + " INTEGER DEFAULT 1)";

    private static final String CREATE_TABLE_REGISTRATIONS = "CREATE TABLE " + TABLE_REGISTRATIONS + "("
            + COLUMN_REGISTRATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_ID + " INTEGER NOT NULL,"
            + COLUMN_OFFERING_ID + " INTEGER NOT NULL,"
            + COLUMN_PROMO_ID + " INTEGER,"
            + COLUMN_AMOUNT_PAID + " REAL NOT NULL,"
            + COLUMN_DISCOUNT_AMOUNT + " REAL DEFAULT 0,"
            + COLUMN_REGISTRATION_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + COLUMN_IS_CONFIRMED + " INTEGER DEFAULT 0,"
            + COLUMN_CONFIRMATION_EMAIL_SENT + " INTEGER DEFAULT 0,"
            + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "),"
            + "FOREIGN KEY (" + COLUMN_OFFERING_ID + ") REFERENCES " + TABLE_COURSE_OFFERINGS + "(" + COLUMN_OFFERING_ID + "),"
            + "FOREIGN KEY (" + COLUMN_PROMO_ID + ") REFERENCES " + TABLE_PROMOTION_CODES + "(" + COLUMN_PROMO_ID + "))";

    private static final String CREATE_TABLE_NOTIFICATIONS = "CREATE TABLE " + TABLE_NOTIFICATIONS + "("
            + COLUMN_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_ID + " INTEGER,"
            + COLUMN_TITLE + " TEXT NOT NULL,"
            + COLUMN_MESSAGE + " TEXT NOT NULL,"
            + COLUMN_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + COLUMN_IS_READ + " INTEGER DEFAULT 0,"
            + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "))";

    // Insert default data
    private static final String INSERT_DEFAULT_PROMOTIONS = "INSERT INTO " + TABLE_PROMOTION_CODES +
            " (" + COLUMN_PROMO_CODE + ", " + COLUMN_DISCOUNT_PERCENTAGE + ", " + COLUMN_DESCRIPTION + ") VALUES " +
            "('M563432', 25, '25% discount'), " +
            "('S663435', 40, '40% discount'), " +
            "('L763434', 60, '60% discount')";

    private static final String INSERT_DEFAULT_BRANCHES = "INSERT INTO " + TABLE_BRANCHES +
            " (" + COLUMN_BRANCH_CODE + ", " + COLUMN_BRANCH_NAME + ", " + COLUMN_LOCATION + ", " +
            COLUMN_LATITUDE + ", " + COLUMN_LONGITUDE + ") VALUES " +
            "('BR001', 'Mathara', 'Mathara Main Street', 5.9497, 80.5429), " +
            "('BR002', 'Kandy', 'Kandy Central', 7.2906, 80.6337), " +
            "('BR003', 'Negombo', 'Negombo Beach Road', 7.2081, 79.8371), " +
            "('BR004', 'Colombo', 'Colombo City Center', 6.9271, 79.8612), " +
            "('BR005', 'Jaffna', 'Jaffna Main Street', 9.6615, 80.0255), " +
            "('BR006', 'Kegalle', 'Kegalle Town', 7.2513, 80.3464), " +
            "('BR007', 'Piliyandala', 'Piliyandala Central', 6.7987, 79.9246), " +
            "('BR008', 'Kottawa', 'Kottawa Junction', 6.8429, 79.9577)";

    private static final String INSERT_ADMIN_USER = "INSERT INTO " + TABLE_USERS +
            " (" + COLUMN_NAME + ", " + COLUMN_ADDRESS + ", " + COLUMN_CITY + ", " +
            COLUMN_DATE_OF_BIRTH + ", " + COLUMN_NIC + ", " + COLUMN_EMAIL + ", " +
            COLUMN_GENDER + ", " + COLUMN_PHONE + ", " + COLUMN_USER_TYPE + ", " + COLUMN_IS_VERIFIED + ") VALUES " +
            "('Admin User', '123 Admin St', 'Colombo', '1990-01-01', 'ADMIN123456', 'admin@example.com', 'Male', '0771234567', 'ADMIN', 1)";

    private static final String INSERT_SAMPLE_COURSE = "INSERT INTO " + TABLE_COURSES +
            " (" + COLUMN_COURSE_NAME + ", " + COLUMN_COURSE_FEE + ", " + COLUMN_DURATION + ", " +
            COLUMN_MAX_PARTICIPANTS + ", " + COLUMN_PUBLISHED_DATE + ") VALUES " +
            "('Information Systems for Beginners', 30000.00, 10, 30, '2024-02-15')";

    private static final String INSERT_SAMPLE_OFFERING = "INSERT INTO " + TABLE_COURSE_OFFERINGS +
            " (" + COLUMN_COURSE_ID + ", " + COLUMN_BRANCH_ID + ", " + COLUMN_START_DATE + ", " +
            COLUMN_REGISTRATION_DEADLINE + ") VALUES " +
            "(1, 4, '2024-03-15', '2024-02-29')";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating required tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_BRANCHES);
        db.execSQL(CREATE_TABLE_COURSES);
        db.execSQL(CREATE_TABLE_COURSE_OFFERINGS);
        db.execSQL(CREATE_TABLE_PROMOTION_CODES);
        db.execSQL(CREATE_TABLE_REGISTRATIONS);
        db.execSQL(CREATE_TABLE_NOTIFICATIONS);

        // Insert default data
        db.execSQL(INSERT_DEFAULT_PROMOTIONS);
        db.execSQL(INSERT_DEFAULT_BRANCHES);
        db.execSQL(INSERT_ADMIN_USER);
        db.execSQL(INSERT_SAMPLE_COURSE);
        db.execSQL(INSERT_SAMPLE_OFFERING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMOTION_CODES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE_OFFERINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRANCHES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    //=================================================================================================
    // MODEL CLASSES
    //=================================================================================================

    /**
     * User model class
     */
    public static class User {
        private long userId;
        private String name;
        private String address;
        private String city;
        private String dateOfBirth;
        private String nic;
        private String email;
        private String gender;
        private String phone;
        private byte[] profilePicture;
        private String verificationCode;
        private boolean isVerified;
        private String userType;
        private String createdAt;

        // Constructor
        public User() {
        }

        // Getters and setters
        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getNic() {
            return nic;
        }

        public void setNic(String nic) {
            this.nic = nic;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public byte[] getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(byte[] profilePicture) {
            this.profilePicture = profilePicture;
        }

        public String getVerificationCode() {
            return verificationCode;
        }

        public void setVerificationCode(String verificationCode) {
            this.verificationCode = verificationCode;
        }

        public boolean isVerified() {
            return isVerified;
        }

        public void setVerified(boolean verified) {
            isVerified = verified;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }

    /**
     * Branch model class
     */
    public static class Branch {
        private long branchId;
        private String branchCode;
        private String branchName;
        private String location;
        private double latitude;
        private double longitude;

        // Constructor
        public Branch() {
        }

        // Getters and setters
        public long getBranchId() {
            return branchId;
        }

        public void setBranchId(long branchId) {
            this.branchId = branchId;
        }

        public String getBranchCode() {
            return branchCode;
        }

        public void setBranchCode(String branchCode) {
            this.branchCode = branchCode;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

    /**
     * Course model class
     */
    public static class Course {
        private long courseId;
        private String courseName;
        private double courseFee;
        private int duration;
        private int maxParticipants;
        private String publishedDate;
        private String createdAt;

        // Constructor
        public Course() {
        }

        // Getters and setters
        public long getCourseId() {
            return courseId;
        }

        public void setCourseId(long courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public double getCourseFee() {
            return courseFee;
        }

        public void setCourseFee(double courseFee) {
            this.courseFee = courseFee;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getMaxParticipants() {
            return maxParticipants;
        }

        public void setMaxParticipants(int maxParticipants) {
            this.maxParticipants = maxParticipants;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }

    /**
     * Course Offering model class
     */
    public static class CourseOffering {
        private long offeringId;
        private long courseId;
        private long branchId;
        private String startDate;
        private String registrationDeadline;
        private boolean isActive;

        // Extra fields for displaying joined data
        private String courseName;
        private String branchName;
        private double courseFee;
        private int duration;

        // Constructor
        public CourseOffering() {
        }

        // Getters and setters
        public long getOfferingId() {
            return offeringId;
        }

        public void setOfferingId(long offeringId) {
            this.offeringId = offeringId;
        }

        public long getCourseId() {
            return courseId;
        }

        public void setCourseId(long courseId) {
            this.courseId = courseId;
        }

        public long getBranchId() {
            return branchId;
        }

        public void setBranchId(long branchId) {
            this.branchId = branchId;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getRegistrationDeadline() {
            return registrationDeadline;
        }

        public void setRegistrationDeadline(String registrationDeadline) {
            this.registrationDeadline = registrationDeadline;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public double getCourseFee() {
            return courseFee;
        }

        public void setCourseFee(double courseFee) {
            this.courseFee = courseFee;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }

    /**
     * Promotion Code model class
     */
    public static class PromotionCode {
        private long promoId;
        private String promoCode;
        private int discountPercentage;
        private String description;
        private boolean isActive;

        // Constructor
        public PromotionCode() {
        }

        // Getters and setters
        public long getPromoId() {
            return promoId;
        }

        public void setPromoId(long promoId) {
            this.promoId = promoId;
        }

        public String getPromoCode() {
            return promoCode;
        }

        public void setPromoCode(String promoCode) {
            this.promoCode = promoCode;
        }

        public int getDiscountPercentage() {
            return discountPercentage;
        }

        public void setDiscountPercentage(int discountPercentage) {
            this.discountPercentage = discountPercentage;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }
    }

    /**
     * Registration model class
     */
    public static class Registration {
        private long registrationId;
        private long userId;
        private long offeringId;
        private Long promoId;  // Can be null
        private double amountPaid;
        private double discountAmount;
        private String registrationDate;
        private boolean isConfirmed;
        private boolean confirmationEmailSent;

        // Extra fields for displaying joined data
        private String userName;
        private String courseName;
        private String branchName;

        // Constructor
        public Registration() {
        }

        // Getters and setters
        public long getRegistrationId() {
            return registrationId;
        }

        public void setRegistrationId(long registrationId) {
            this.registrationId = registrationId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public long getOfferingId() {
            return offeringId;
        }

        public void setOfferingId(long offeringId) {
            this.offeringId = offeringId;
        }

        public Long getPromoId() {
            return promoId;
        }

        public void setPromoId(Long promoId) {
            this.promoId = promoId;
        }

        public double getAmountPaid() {
            return amountPaid;
        }

        public void setAmountPaid(double amountPaid) {
            this.amountPaid = amountPaid;
        }

        public double getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(double discountAmount) {
            this.discountAmount = discountAmount;
        }

        public String getRegistrationDate() {
            return registrationDate;
        }

        public void setRegistrationDate(String registrationDate) {
            this.registrationDate = registrationDate;
        }

        public boolean isConfirmed() {
            return isConfirmed;
        }

        public void setConfirmed(boolean confirmed) {
            isConfirmed = confirmed;
        }

        public boolean isConfirmationEmailSent() {
            return confirmationEmailSent;
        }

        public void setConfirmationEmailSent(boolean confirmationEmailSent) {
            this.confirmationEmailSent = confirmationEmailSent;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }
    }

    /**
     * Notification model class
     */
    public static class Notification {
        private long notificationId;
        private Long userId;  // Can be null for broadcast notifications
        private String title;
        private String message;
        private String createdAt;
        private boolean isRead;

        // Constructor
        public Notification() {
        }

        // Getters and setters
        public long getNotificationId() {
            return notificationId;
        }

        public void setNotificationId(long notificationId) {
            this.notificationId = notificationId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public boolean isRead() {
            return isRead;
        }

        public void setRead(boolean read) {
            isRead = read;
        }
    }

    //=================================================================================================
    // CRUD OPERATIONS
    //=================================================================================================

    //-----------------------------------
    // User operations
    //-----------------------------------

    /**
     * Create a new user
     */
    public long createUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_ADDRESS, user.getAddress());
        values.put(COLUMN_CITY, user.getCity());
        values.put(COLUMN_DATE_OF_BIRTH, user.getDateOfBirth());
        values.put(COLUMN_NIC, user.getNic());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_GENDER, user.getGender());
        values.put(COLUMN_PHONE, user.getPhone());
        values.put(COLUMN_PROFILE_PICTURE, user.getProfilePicture());
        values.put(COLUMN_VERIFICATION_CODE, user.getVerificationCode());
        values.put(COLUMN_IS_VERIFIED, user.isVerified() ? 1 : 0);
        values.put(COLUMN_USER_TYPE, user.getUserType());

        // Insert row
        long id = db.insert(TABLE_USERS, null, values);
        db.close();

        return id;
    }

    /**
     * Get user by id
     */
    public User getUser(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{COLUMN_USER_ID, COLUMN_NAME, COLUMN_ADDRESS, COLUMN_CITY,
                        COLUMN_DATE_OF_BIRTH, COLUMN_NIC, COLUMN_EMAIL, COLUMN_GENDER,
                        COLUMN_PHONE, COLUMN_PROFILE_PICTURE, COLUMN_VERIFICATION_CODE,
                        COLUMN_IS_VERIFIED, COLUMN_USER_TYPE, COLUMN_CREATED_AT},
                COLUMN_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null, null, null, null);

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User();

            int userIdIdx = cursor.getColumnIndex(COLUMN_USER_ID);
            if (userIdIdx >= 0) {
                user.setUserId(cursor.getLong(userIdIdx));
            }

            int nameIdx = cursor.getColumnIndex(COLUMN_NAME);
            if (nameIdx >= 0) {
                user.setName(cursor.getString(nameIdx));
            }

            int addressIdx = cursor.getColumnIndex(COLUMN_ADDRESS);
            if (addressIdx >= 0) {
                user.setAddress(cursor.getString(addressIdx));
            }

            int cityIdx = cursor.getColumnIndex(COLUMN_CITY);
            if (cityIdx >= 0) {
                user.setCity(cursor.getString(cityIdx));
            }

            int dobIdx = cursor.getColumnIndex(COLUMN_DATE_OF_BIRTH);
            if (dobIdx >= 0) {
                user.setDateOfBirth(cursor.getString(dobIdx));
            }

            int nicIdx = cursor.getColumnIndex(COLUMN_NIC);
            if (nicIdx >= 0) {
                user.setNic(cursor.getString(nicIdx));
            }

            int emailIdx = cursor.getColumnIndex(COLUMN_EMAIL);
            if (emailIdx >= 0) {
                user.setEmail(cursor.getString(emailIdx));
            }

            int genderIdx = cursor.getColumnIndex(COLUMN_GENDER);
            if (genderIdx >= 0) {
                user.setGender(cursor.getString(genderIdx));
            }

            int phoneIdx = cursor.getColumnIndex(COLUMN_PHONE);
            if (phoneIdx >= 0) {
                user.setPhone(cursor.getString(phoneIdx));
            }

            int profilePictureIdx = cursor.getColumnIndex(COLUMN_PROFILE_PICTURE);
            if (profilePictureIdx >= 0) {
                user.setProfilePicture(cursor.getBlob(profilePictureIdx));
            }

            int verificationCodeIdx = cursor.getColumnIndex(COLUMN_VERIFICATION_CODE);
            if (verificationCodeIdx >= 0) {
                user.setVerificationCode(cursor.getString(verificationCodeIdx));
            }

            int isVerifiedIdx = cursor.getColumnIndex(COLUMN_IS_VERIFIED);
            if (isVerifiedIdx >= 0) {
                user.setVerified(cursor.getInt(isVerifiedIdx) == 1);
            }

            int userTypeIdx = cursor.getColumnIndex(COLUMN_USER_TYPE);
            if (userTypeIdx >= 0) {
                user.setUserType(cursor.getString(userTypeIdx));
            }

            int createdAtIdx = cursor.getColumnIndex(COLUMN_CREATED_AT);
            if (createdAtIdx >= 0) {
                user.setCreatedAt(cursor.getString(createdAtIdx));
            }

            cursor.close();
        }

        db.close();
        return user;
    }

    /**
     * Get user by email
     */
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{COLUMN_USER_ID, COLUMN_NAME, COLUMN_ADDRESS, COLUMN_CITY,
                        COLUMN_DATE_OF_BIRTH, COLUMN_NIC, COLUMN_EMAIL, COLUMN_GENDER,
                        COLUMN_PHONE, COLUMN_PROFILE_PICTURE, COLUMN_VERIFICATION_CODE,
                        COLUMN_IS_VERIFIED, COLUMN_USER_TYPE, COLUMN_CREATED_AT},
                COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null, null);

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            int userIdIdx = cursor.getColumnIndex(COLUMN_USER_ID);
            int nameIdx = cursor.getColumnIndex(COLUMN_NAME);
            int addressIdx = cursor.getColumnIndex(COLUMN_ADDRESS);
            int cityIdx = cursor.getColumnIndex(COLUMN_CITY);
            int dobIdx = cursor.getColumnIndex(COLUMN_DATE_OF_BIRTH);
            int nicIdx = cursor.getColumnIndex(COLUMN_NIC);
            int emailIdx = cursor.getColumnIndex(COLUMN_EMAIL);
            int genderIdx = cursor.getColumnIndex(COLUMN_GENDER);
            int phoneIdx = cursor.getColumnIndex(COLUMN_PHONE);
            int profilePicIdx = cursor.getColumnIndex(COLUMN_PROFILE_PICTURE);
            int verificationCodeIdx = cursor.getColumnIndex(COLUMN_VERIFICATION_CODE);
            int isVerifiedIdx = cursor.getColumnIndex(COLUMN_IS_VERIFIED);
            int userTypeIdx = cursor.getColumnIndex(COLUMN_USER_TYPE);
            int createdAtIdx = cursor.getColumnIndex(COLUMN_CREATED_AT);

            if (userIdIdx >= 0) user.setUserId(cursor.getLong(userIdIdx));
            if (nameIdx >= 0) user.setName(cursor.getString(nameIdx));
            if (addressIdx >= 0) user.setAddress(cursor.getString(addressIdx));
            if (cityIdx >= 0) user.setCity(cursor.getString(cityIdx));
            if (dobIdx >= 0) user.setDateOfBirth(cursor.getString(dobIdx));
            if (nicIdx >= 0) user.setNic(cursor.getString(nicIdx));
            if (emailIdx >= 0) user.setEmail(cursor.getString(emailIdx));
            if (genderIdx >= 0) user.setGender(cursor.getString(genderIdx));
            if (phoneIdx >= 0) user.setPhone(cursor.getString(phoneIdx));
            if (profilePicIdx >= 0) user.setProfilePicture(cursor.getBlob(profilePicIdx));
            if (verificationCodeIdx >= 0)
                user.setVerificationCode(cursor.getString(verificationCodeIdx));
            if (isVerifiedIdx >= 0) user.setVerified(cursor.getInt(isVerifiedIdx) == 1);
            if (userTypeIdx >= 0) user.setUserType(cursor.getString(userTypeIdx));
            if (createdAtIdx >= 0) user.setCreatedAt(cursor.getString(createdAtIdx));
            cursor.close();
        }

        db.close();
        return user;
    }

    /**
     * Update user verification status
     */
    public int updateUserVerification(long userId, boolean isVerified) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_VERIFIED, isVerified ? 1 : 0);

        // Update row
        int result = db.update(TABLE_USERS, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(userId)});

        db.close();
        return result;
    }

    /**
     * Update user profile picture
     */
    public int updateUserProfilePicture(long userId, byte[] profilePicture) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PROFILE_PICTURE, profilePicture);

        // Update row
        int result = db.update(TABLE_USERS, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(userId)});

        db.close();
        return result;
    }

    /**
     * Get all users (for admin)
     */
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                int userIdIdx = cursor.getColumnIndex(COLUMN_USER_ID);
                int nameIdx = cursor.getColumnIndex(COLUMN_NAME);
                int addressIdx = cursor.getColumnIndex(COLUMN_ADDRESS);
                int cityIdx = cursor.getColumnIndex(COLUMN_CITY);
                int dobIdx = cursor.getColumnIndex(COLUMN_DATE_OF_BIRTH);
                int nicIdx = cursor.getColumnIndex(COLUMN_NIC);
                int emailIdx = cursor.getColumnIndex(COLUMN_EMAIL);
                int genderIdx = cursor.getColumnIndex(COLUMN_GENDER);
                int phoneIdx = cursor.getColumnIndex(COLUMN_PHONE);
                int verificationCodeIdx = cursor.getColumnIndex(COLUMN_VERIFICATION_CODE);
                int isVerifiedIdx = cursor.getColumnIndex(COLUMN_IS_VERIFIED);
                int userTypeIdx = cursor.getColumnIndex(COLUMN_USER_TYPE);
                int createdAtIdx = cursor.getColumnIndex(COLUMN_CREATED_AT);

                if (userIdIdx >= 0) user.setUserId(cursor.getLong(userIdIdx));
                if (nameIdx >= 0) user.setName(cursor.getString(nameIdx));
                if (addressIdx >= 0) user.setAddress(cursor.getString(addressIdx));
                if (cityIdx >= 0) user.setCity(cursor.getString(cityIdx));
                if (dobIdx >= 0) user.setDateOfBirth(cursor.getString(dobIdx));
                if (nicIdx >= 0) user.setNic(cursor.getString(nicIdx));
                if (emailIdx >= 0) user.setEmail(cursor.getString(emailIdx));
                if (genderIdx >= 0) user.setGender(cursor.getString(genderIdx));
                if (phoneIdx >= 0) user.setPhone(cursor.getString(phoneIdx));
                // Skip the profile picture to avoid memory issues when loading all users
                if (verificationCodeIdx >= 0)
                    user.setVerificationCode(cursor.getString(verificationCodeIdx));
                if (isVerifiedIdx >= 0) user.setVerified(cursor.getInt(isVerifiedIdx) == 1);
                if (userTypeIdx >= 0) user.setUserType(cursor.getString(userTypeIdx));
                if (createdAtIdx >= 0) user.setCreatedAt(cursor.getString(createdAtIdx));

                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return userList;
    }

//-----------------------------------
// Branch operations
//-----------------------------------

    /**
     * Create a new branch
     */
    public long createBranch(Branch branch) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BRANCH_CODE, branch.getBranchCode());
        values.put(COLUMN_BRANCH_NAME, branch.getBranchName());
        values.put(COLUMN_LOCATION, branch.getLocation());
        values.put(COLUMN_LATITUDE, branch.getLatitude());
        values.put(COLUMN_LONGITUDE, branch.getLongitude());

        // Insert row
        long id = db.insert(TABLE_BRANCHES, null, values);
        db.close();

        return id;
    }

    /**
     * Get branch by id
     */
    public Branch getBranch(long branchId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_BRANCHES,
                new String[]{COLUMN_BRANCH_ID, COLUMN_BRANCH_CODE, COLUMN_BRANCH_NAME,
                        COLUMN_LOCATION, COLUMN_LATITUDE, COLUMN_LONGITUDE},
                COLUMN_BRANCH_ID + "=?",
                new String[]{String.valueOf(branchId)},
                null, null, null, null);

        Branch branch = null;
        if (cursor != null && cursor.moveToFirst()) {
            branch = new Branch();
            int branchIdIdx = cursor.getColumnIndex(COLUMN_BRANCH_ID);
            int branchCodeIdx = cursor.getColumnIndex(COLUMN_BRANCH_CODE);
            int branchNameIdx = cursor.getColumnIndex(COLUMN_BRANCH_NAME);
            int locationIdx = cursor.getColumnIndex(COLUMN_LOCATION);
            int latitudeIdx = cursor.getColumnIndex(COLUMN_LATITUDE);
            int longitudeIdx = cursor.getColumnIndex(COLUMN_LONGITUDE);

            if (branchIdIdx >= 0) branch.setBranchId(cursor.getLong(branchIdIdx));
            if (branchCodeIdx >= 0) branch.setBranchCode(cursor.getString(branchCodeIdx));
            if (branchNameIdx >= 0) branch.setBranchName(cursor.getString(branchNameIdx));
            if (locationIdx >= 0) branch.setLocation(cursor.getString(locationIdx));
            if (latitudeIdx >= 0) branch.setLatitude(cursor.getDouble(latitudeIdx));
            if (longitudeIdx >= 0) branch.setLongitude(cursor.getDouble(longitudeIdx));
            cursor.close();
        }

        db.close();
        return branch;
    }

    /**
     * Get all branches
     */
    public List<Branch> getAllBranches() {
        List<Branch> branchList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_BRANCHES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                Branch branch = new Branch();
                int branchIdIdx = cursor.getColumnIndex(COLUMN_BRANCH_ID);
                int branchCodeIdx = cursor.getColumnIndex(COLUMN_BRANCH_CODE);
                int branchNameIdx = cursor.getColumnIndex(COLUMN_BRANCH_NAME);
                int locationIdx = cursor.getColumnIndex(COLUMN_LOCATION);
                int latitudeIdx = cursor.getColumnIndex(COLUMN_LATITUDE);
                int longitudeIdx = cursor.getColumnIndex(COLUMN_LONGITUDE);

                if (branchIdIdx >= 0) branch.setBranchId(cursor.getLong(branchIdIdx));
                if (branchCodeIdx >= 0) branch.setBranchCode(cursor.getString(branchCodeIdx));
                if (branchNameIdx >= 0) branch.setBranchName(cursor.getString(branchNameIdx));
                if (locationIdx >= 0) branch.setLocation(cursor.getString(locationIdx));
                if (latitudeIdx >= 0) branch.setLatitude(cursor.getDouble(latitudeIdx));
                if (longitudeIdx >= 0) branch.setLongitude(cursor.getDouble(longitudeIdx));

                branchList.add(branch);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return branchList;
    }

//-----------------------------------
// Course operations
//-----------------------------------

    /**
     * Create a new course
     */
    public long createCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_COURSE_NAME, course.getCourseName());
        values.put(COLUMN_COURSE_FEE, course.getCourseFee());
        values.put(COLUMN_DURATION, course.getDuration());
        values.put(COLUMN_MAX_PARTICIPANTS, course.getMaxParticipants());
        values.put(COLUMN_PUBLISHED_DATE, course.getPublishedDate());

        // Insert row
        long id = db.insert(TABLE_COURSES, null, values);
        db.close();

        return id;
    }

    /**
     * Get course by id
     */
    public Course getCourse(long courseId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_COURSES,
                new String[]{COLUMN_COURSE_ID, COLUMN_COURSE_NAME, COLUMN_COURSE_FEE,
                        COLUMN_DURATION, COLUMN_MAX_PARTICIPANTS, COLUMN_PUBLISHED_DATE, COLUMN_CREATED_AT},
                COLUMN_COURSE_ID + "=?",
                new String[]{String.valueOf(courseId)},
                null, null, null, null);

        Course course = null;
        if (cursor != null && cursor.moveToFirst()) {
            course = new Course();
            int courseIdIdx = cursor.getColumnIndex(COLUMN_COURSE_ID);
            int courseNameIdx = cursor.getColumnIndex(COLUMN_COURSE_NAME);
            int courseFeeIdx = cursor.getColumnIndex(COLUMN_COURSE_FEE);
            int durationIdx = cursor.getColumnIndex(COLUMN_DURATION);
            int maxParticipantsIdx = cursor.getColumnIndex(COLUMN_MAX_PARTICIPANTS);
            int publishedDateIdx = cursor.getColumnIndex(COLUMN_PUBLISHED_DATE);
            int createdAtIdx = cursor.getColumnIndex(COLUMN_CREATED_AT);

            if (courseIdIdx >= 0) course.setCourseId(cursor.getLong(courseIdIdx));
            if (courseNameIdx >= 0) course.setCourseName(cursor.getString(courseNameIdx));
            if (courseFeeIdx >= 0) course.setCourseFee(cursor.getDouble(courseFeeIdx));
            if (durationIdx >= 0) course.setDuration(cursor.getInt(durationIdx));
            if (maxParticipantsIdx >= 0)
                course.setMaxParticipants(cursor.getInt(maxParticipantsIdx));
            if (publishedDateIdx >= 0) course.setPublishedDate(cursor.getString(publishedDateIdx));
            if (createdAtIdx >= 0) course.setCreatedAt(cursor.getString(createdAtIdx));
            cursor.close();
        }

        db.close();
        return course;
    }

    /**
     * Get all courses
     */
    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_COURSES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                Course course = new Course();
                int courseIdIdx = cursor.getColumnIndex(COLUMN_COURSE_ID);
                int courseNameIdx = cursor.getColumnIndex(COLUMN_COURSE_NAME);
                int courseFeeIdx = cursor.getColumnIndex(COLUMN_COURSE_FEE);
                int durationIdx = cursor.getColumnIndex(COLUMN_DURATION);
                int maxParticipantsIdx = cursor.getColumnIndex(COLUMN_MAX_PARTICIPANTS);
                int publishedDateIdx = cursor.getColumnIndex(COLUMN_PUBLISHED_DATE);
                int createdAtIdx = cursor.getColumnIndex(COLUMN_CREATED_AT);

                if (courseIdIdx >= 0) course.setCourseId(cursor.getLong(courseIdIdx));
                if (courseNameIdx >= 0) course.setCourseName(cursor.getString(courseNameIdx));
                if (courseFeeIdx >= 0) course.setCourseFee(cursor.getDouble(courseFeeIdx));
                if (durationIdx >= 0) course.setDuration(cursor.getInt(durationIdx));
                if (maxParticipantsIdx >= 0)
                    course.setMaxParticipants(cursor.getInt(maxParticipantsIdx));
                if (publishedDateIdx >= 0)
                    course.setPublishedDate(cursor.getString(publishedDateIdx));
                if (createdAtIdx >= 0) course.setCreatedAt(cursor.getString(createdAtIdx));

                courseList.add(course);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return courseList;
    }

//-----------------------------------
// Course Offering operations
//-----------------------------------

    /**
     * Create a new course offering
     */
    public long createCourseOffering(CourseOffering offering) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_COURSE_ID, offering.getCourseId());
        values.put(COLUMN_BRANCH_ID, offering.getBranchId());
        values.put(COLUMN_START_DATE, offering.getStartDate());
        values.put(COLUMN_REGISTRATION_DEADLINE, offering.getRegistrationDeadline());
        values.put(COLUMN_IS_ACTIVE, offering.isActive() ? 1 : 0);

        // Insert row
        long id = db.insert(TABLE_COURSE_OFFERINGS, null, values);
        db.close();

        return id;
    }

    /**
     * Get course offering by id
     */
    public CourseOffering getCourseOffering(long offeringId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT o.*, c.course_name, c.course_fee, c.duration, b.branch_name " +
                "FROM " + TABLE_COURSE_OFFERINGS + " o " +
                "JOIN " + TABLE_COURSES + " c ON o.course_id = c.course_id " +
                "JOIN " + TABLE_BRANCHES + " b ON o.branch_id = b.branch_id " +
                "WHERE o." + COLUMN_OFFERING_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(offeringId)});

        CourseOffering offering = null;
        if (cursor != null && cursor.moveToFirst()) {
            offering = new CourseOffering();
            int offeringIdIdx = cursor.getColumnIndex(COLUMN_OFFERING_ID);
            int courseIdIdx = cursor.getColumnIndex(COLUMN_COURSE_ID);
            int branchIdIdx = cursor.getColumnIndex(COLUMN_BRANCH_ID);
            int startDateIdx = cursor.getColumnIndex(COLUMN_START_DATE);
            int regDeadlineIdx = cursor.getColumnIndex(COLUMN_REGISTRATION_DEADLINE);
            int isActiveIdx = cursor.getColumnIndex(COLUMN_IS_ACTIVE);
            int courseNameIdx = cursor.getColumnIndex(COLUMN_COURSE_NAME);
            int courseFeeIdx = cursor.getColumnIndex(COLUMN_COURSE_FEE);
            int durationIdx = cursor.getColumnIndex(COLUMN_DURATION);
            int branchNameIdx = cursor.getColumnIndex(COLUMN_BRANCH_NAME);

            if (offeringIdIdx >= 0) offering.setOfferingId(cursor.getLong(offeringIdIdx));
            if (courseIdIdx >= 0) offering.setCourseId(cursor.getLong(courseIdIdx));
            if (branchIdIdx >= 0) offering.setBranchId(cursor.getLong(branchIdIdx));
            if (startDateIdx >= 0) offering.setStartDate(cursor.getString(startDateIdx));
            if (regDeadlineIdx >= 0)
                offering.setRegistrationDeadline(cursor.getString(regDeadlineIdx));
            if (isActiveIdx >= 0) offering.setActive(cursor.getInt(isActiveIdx) == 1);

            // Get joined data
            if (courseNameIdx >= 0) offering.setCourseName(cursor.getString(courseNameIdx));
            if (courseFeeIdx >= 0) offering.setCourseFee(cursor.getDouble(courseFeeIdx));
            if (durationIdx >= 0) offering.setDuration(cursor.getInt(durationIdx));
            if (branchNameIdx >= 0) offering.setBranchName(cursor.getString(branchNameIdx));
            cursor.close();
        }

        db.close();
        return offering;
    }

    /**
     * Get all active course offerings
     */
    public List<CourseOffering> getAllActiveCourseOfferings() {
        List<CourseOffering> offeringList = new ArrayList<>();

        String query = "SELECT o.*, c.course_name, c.course_fee, c.duration, b.branch_name " +
                "FROM " + TABLE_COURSE_OFFERINGS + " o " +
                "JOIN " + TABLE_COURSES + " c ON o.course_id = c.course_id " +
                "JOIN " + TABLE_BRANCHES + " b ON o.branch_id = b.branch_id " +
                "WHERE o." + COLUMN_IS_ACTIVE + " = 1";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                CourseOffering offering = new CourseOffering();
                int offeringIdIdx = cursor.getColumnIndex(COLUMN_OFFERING_ID);
                int courseIdIdx = cursor.getColumnIndex(COLUMN_COURSE_ID);
                int branchIdIdx = cursor.getColumnIndex(COLUMN_BRANCH_ID);
                int startDateIdx = cursor.getColumnIndex(COLUMN_START_DATE);
                int regDeadlineIdx = cursor.getColumnIndex(COLUMN_REGISTRATION_DEADLINE);
                int isActiveIdx = cursor.getColumnIndex(COLUMN_IS_ACTIVE);
                int courseNameIdx = cursor.getColumnIndex(COLUMN_COURSE_NAME);
                int courseFeeIdx = cursor.getColumnIndex(COLUMN_COURSE_FEE);
                int durationIdx = cursor.getColumnIndex(COLUMN_DURATION);
                int branchNameIdx = cursor.getColumnIndex(COLUMN_BRANCH_NAME);

                if (offeringIdIdx >= 0) offering.setOfferingId(cursor.getLong(offeringIdIdx));
                if (courseIdIdx >= 0) offering.setCourseId(cursor.getLong(courseIdIdx));
                if (branchIdIdx >= 0) offering.setBranchId(cursor.getLong(branchIdIdx));
                if (startDateIdx >= 0) offering.setStartDate(cursor.getString(startDateIdx));
                if (regDeadlineIdx >= 0)
                    offering.setRegistrationDeadline(cursor.getString(regDeadlineIdx));
                if (isActiveIdx >= 0) offering.setActive(cursor.getInt(isActiveIdx) == 1);

                // Get joined data
                if (courseNameIdx >= 0) offering.setCourseName(cursor.getString(courseNameIdx));
                if (courseFeeIdx >= 0) offering.setCourseFee(cursor.getDouble(courseFeeIdx));
                if (durationIdx >= 0) offering.setDuration(cursor.getInt(durationIdx));
                if (branchNameIdx >= 0) offering.setBranchName(cursor.getString(branchNameIdx));

                offeringList.add(offering);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return offeringList;
    }

    /**
     * Update course offering status
     */
    public int updateCourseOfferingStatus(long offeringId, boolean isActive) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_ACTIVE, isActive ? 1 : 0);

        // Update row
        int result = db.update(TABLE_COURSE_OFFERINGS, values, COLUMN_OFFERING_ID + " = ?",
                new String[]{String.valueOf(offeringId)});

        db.close();
        return result;
    }

//-----------------------------------
// Promotion Code operations
//-----------------------------------

    /**
     * Get promotion code by code
     */
    public PromotionCode getPromotionByCode(String promoCode) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PROMOTION_CODES,
                new String[]{COLUMN_PROMO_ID, COLUMN_PROMO_CODE, COLUMN_DISCOUNT_PERCENTAGE,
                        COLUMN_DESCRIPTION, COLUMN_IS_ACTIVE},
                COLUMN_PROMO_CODE + "=?",
                new String[]{promoCode},
                null, null, null, null);

        PromotionCode promotion = null;
        if (cursor != null && cursor.moveToFirst()) {
            promotion = new PromotionCode();
            int promoIdIdx = cursor.getColumnIndex(COLUMN_PROMO_ID);
            int promoCodeIdx = cursor.getColumnIndex(COLUMN_PROMO_CODE);
            int discountPercentageIdx = cursor.getColumnIndex(COLUMN_DISCOUNT_PERCENTAGE);
            int descriptionIdx = cursor.getColumnIndex(COLUMN_DESCRIPTION);
            int isActiveIdx = cursor.getColumnIndex(COLUMN_IS_ACTIVE);

            if (promoIdIdx >= 0) promotion.setPromoId(cursor.getLong(promoIdIdx));
            if (promoCodeIdx >= 0) promotion.setPromoCode(cursor.getString(promoCodeIdx));
            if (discountPercentageIdx >= 0)
                promotion.setDiscountPercentage(cursor.getInt(discountPercentageIdx));
            if (descriptionIdx >= 0) promotion.setDescription(cursor.getString(descriptionIdx));
            if (isActiveIdx >= 0) promotion.setActive(cursor.getInt(isActiveIdx) == 1);
            cursor.close();
        }

        db.close();
        return promotion;
    }

//-----------------------------------
// Registration operations
//-----------------------------------

    /**
     * Create a new registration
     */
    public long createRegistration(Registration registration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_ID, registration.getUserId());
        values.put(COLUMN_OFFERING_ID, registration.getOfferingId());
        if (registration.getPromoId() != null) {
            values.put(COLUMN_PROMO_ID, registration.getPromoId());
        }
        values.put(COLUMN_AMOUNT_PAID, registration.getAmountPaid());
        values.put(COLUMN_DISCOUNT_AMOUNT, registration.getDiscountAmount());
        values.put(COLUMN_IS_CONFIRMED, registration.isConfirmed() ? 1 : 0);
        values.put(COLUMN_CONFIRMATION_EMAIL_SENT, registration.isConfirmationEmailSent() ? 1 : 0);

        // Insert row
        long id = db.insert(TABLE_REGISTRATIONS, null, values);
        db.close();

        return id;
    }

    /**
     * Get all registrations for a user
     */
    public List<Registration> getUserRegistrations(long userId) {
        List<Registration> registrationList = new ArrayList<>();

        String query = "SELECT r.*, u.name as user_name, c.course_name, b.branch_name " +
                "FROM " + TABLE_REGISTRATIONS + " r " +
                "JOIN " + TABLE_USERS + " u ON r.user_id = u.user_id " +
                "JOIN " + TABLE_COURSE_OFFERINGS + " o ON r.offering_id = o.offering_id " +
                "JOIN " + TABLE_COURSES + " c ON o.course_id = c.course_id " +
                "JOIN " + TABLE_BRANCHES + " b ON o.branch_id = b.branch_id " +
                "WHERE r." + COLUMN_USER_ID + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                Registration registration = new Registration();
                int regIdIdx = cursor.getColumnIndex(COLUMN_REGISTRATION_ID);
                int userIdIdx = cursor.getColumnIndex(COLUMN_USER_ID);
                int offeringIdIdx = cursor.getColumnIndex(COLUMN_OFFERING_ID);
                int promoIdIdx = cursor.getColumnIndex(COLUMN_PROMO_ID);
                int amountPaidIdx = cursor.getColumnIndex(COLUMN_AMOUNT_PAID);
                int discountAmountIdx = cursor.getColumnIndex(COLUMN_DISCOUNT_AMOUNT);
                int regDateIdx = cursor.getColumnIndex(COLUMN_REGISTRATION_DATE);
                int isConfirmedIdx = cursor.getColumnIndex(COLUMN_IS_CONFIRMED);
                int emailSentIdx = cursor.getColumnIndex(COLUMN_CONFIRMATION_EMAIL_SENT);
                int userNameIdx = cursor.getColumnIndex("user_name");
                int courseNameIdx = cursor.getColumnIndex(COLUMN_COURSE_NAME);
                int branchNameIdx = cursor.getColumnIndex(COLUMN_BRANCH_NAME);

                if (regIdIdx >= 0) registration.setRegistrationId(cursor.getLong(regIdIdx));
                if (userIdIdx >= 0) registration.setUserId(cursor.getLong(userIdIdx));
                if (offeringIdIdx >= 0) registration.setOfferingId(cursor.getLong(offeringIdIdx));

                if (promoIdIdx >= 0 && !cursor.isNull(promoIdIdx)) {
                    registration.setPromoId(cursor.getLong(promoIdIdx));
                }

                if (amountPaidIdx >= 0) registration.setAmountPaid(cursor.getDouble(amountPaidIdx));
                if (discountAmountIdx >= 0)
                    registration.setDiscountAmount(cursor.getDouble(discountAmountIdx));
                if (regDateIdx >= 0) registration.setRegistrationDate(cursor.getString(regDateIdx));
                if (isConfirmedIdx >= 0)
                    registration.setConfirmed(cursor.getInt(isConfirmedIdx) == 1);
                if (emailSentIdx >= 0)
                    registration.setConfirmationEmailSent(cursor.getInt(emailSentIdx) == 1);

                // Get joined data
                if (userNameIdx >= 0) registration.setUserName(cursor.getString(userNameIdx));
                if (courseNameIdx >= 0) registration.setCourseName(cursor.getString(courseNameIdx));
                if (branchNameIdx >= 0) registration.setBranchName(cursor.getString(branchNameIdx));

                registrationList.add(registration);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return registrationList;
    }

    /**
     * Update registration confirmation status
     */
    public int updateRegistrationConfirmation(long registrationId, boolean isConfirmed, boolean emailSent) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_CONFIRMED, isConfirmed ? 1 : 0);
        values.put(COLUMN_CONFIRMATION_EMAIL_SENT, emailSent ? 1 : 0);

        // Update row
        int result = db.update(TABLE_REGISTRATIONS, values, COLUMN_REGISTRATION_ID + " = ?",
                new String[]{String.valueOf(registrationId)});

        db.close();
        return result;
    }

//-----------------------------------
// Notification operations
//-----------------------------------

    /**
     * Create a new notification
     */
    public long createNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (notification.getUserId() != null) {
            values.put(COLUMN_USER_ID, notification.getUserId());
        }
        values.put(COLUMN_TITLE, notification.getTitle());
        values.put(COLUMN_MESSAGE, notification.getMessage());
        values.put(COLUMN_IS_READ, notification.isRead() ? 1 : 0);

        // Insert row
        long id = db.insert(TABLE_NOTIFICATIONS, null, values);
        db.close();

        return id;
    }

    /**
     * Get all notifications for a user
     */
    public List<Notification> getUserNotifications(long userId) {
        List<Notification> notificationList = new ArrayList<>();

        // Get both user-specific and broadcast notifications
        String selectQuery = "SELECT * FROM " + TABLE_NOTIFICATIONS +
                " WHERE " + COLUMN_USER_ID + " = " + userId +
                " OR " + COLUMN_USER_ID + " IS NULL" +
                " ORDER BY " + COLUMN_CREATED_AT + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                Notification notification = new Notification();
                int notificationIdIdx = cursor.getColumnIndex(COLUMN_NOTIFICATION_ID);
                int userIdIdx = cursor.getColumnIndex(COLUMN_USER_ID);
                int titleIdx = cursor.getColumnIndex(COLUMN_TITLE);
                int messageIdx = cursor.getColumnIndex(COLUMN_MESSAGE);
                int createdAtIdx = cursor.getColumnIndex(COLUMN_CREATED_AT);
                int isReadIdx = cursor.getColumnIndex(COLUMN_IS_READ);

                if (notificationIdIdx >= 0)
                    notification.setNotificationId(cursor.getLong(notificationIdIdx));

                if (userIdIdx >= 0 && !cursor.isNull(userIdIdx)) {
                    notification.setUserId(cursor.getLong(userIdIdx));
                }

                if (titleIdx >= 0) notification.setTitle(cursor.getString(titleIdx));
                if (messageIdx >= 0) notification.setMessage(cursor.getString(messageIdx));
                if (createdAtIdx >= 0) notification.setCreatedAt(cursor.getString(createdAtIdx));
                if (isReadIdx >= 0) notification.setRead(cursor.getInt(isReadIdx) == 1);

                notificationList.add(notification);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return notificationList;
    }

    /**
     * Mark notification as read
     */
    public int markNotificationAsRead(long notificationId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_READ, 1);

        // Update row
        int result = db.update(TABLE_NOTIFICATIONS, values, COLUMN_NOTIFICATION_ID + " = ?",
                new String[]{String.valueOf(notificationId)});

        db.close();
        return result;
    }

    /**
     * Create a broadcast notification for all users
     */
    public long createBroadcastNotification(String title, String message) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setRead(false);
        // No user ID means broadcast

        return createNotification(notification);
    }

    /**
     * Create course notification when a new course is published
     */
    public long createCourseNotification(Course course) {
        String title = "New Course Available!";
        String message = "A new course '" + course.getCourseName() + "' has been published. Registration is now open!";

        return createBroadcastNotification(title, message);
    }
}