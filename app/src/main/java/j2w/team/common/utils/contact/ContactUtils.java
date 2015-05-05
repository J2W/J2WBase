package j2w.team.common.utils.contact;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;

/**
 * @创建人 sky
 * @创建时间 15/5/4 下午5:26
 * @类描述 电话本工具类
 */
public class ContactUtils {

    private static final ContactUtils instance = new ContactUtils();
    private Context context;

    public static ContactUtils getInstance(Context context) {
        instance.setContext(context);
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 获取联系人头像
     *
     * @param id
     * @return
     */
    public Bitmap getContactPhotoByContactId(String id) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream photoInputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), contactUri);
        Bitmap photo = BitmapFactory.decodeStream(photoInputStream);
        if (photo != null) {
            return photo;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), 0);
        return bitmap;
    }

    /**
     * 根据名称搜索
     *
     * @param partialName
     * @return
     */
    public PhoneContact getPhoneContactByName(String partialName) {
        PhoneContact contact = new PhoneContact();

        ContentResolver contentResolver = context.getContentResolver();
        Cursor c = contentResolver.query(Contacts.CONTENT_URI, new String[]{Contacts._ID, Contacts.DISPLAY_NAME_PRIMARY}, Contacts.DISPLAY_NAME_PRIMARY + "LIKE ?", new String[]{partialName},
                null);

        if (c.moveToFirst()) {
            while (c.moveToNext()) {
                String contactId = c.getString(c.getColumnIndex(Contacts.LOOKUP_KEY));

                String contactName = c.getString(c.getColumnIndex(Contacts.DISPLAY_NAME_PRIMARY));
                contact.contactId = contactId;
                Bitmap photo = getContactPhotoByContactId(contactId);

                contact.displayName = contactName;
                contact.photo = photo;
                contact.phoneNumbers = getContactPhoneNumbersById(contactId);
                contact.emailAddresses = getContactEmailByContactId(contactId);
            }
            c.close();
        }
        return contact;
    }

    /**
     * 获取所有联系人
     *
     * @return
     */
    public List<PhoneContact> getAllPhoneContacts(String userName) {
        List<PhoneContact> contacts = new ArrayList<PhoneContact>();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("%");
        stringBuilder.append(userName);
        stringBuilder.append("%");

        ContentResolver contentResolver = context.getContentResolver();
        Cursor idCursor = contentResolver.query(Contacts.CONTENT_URI, new String[]{Contacts._ID, Contacts.DISPLAY_NAME_PRIMARY}, Contacts.DISPLAY_NAME_PRIMARY + "LIKE ?", new String[]{stringBuilder.toString()}, null);

        if (idCursor.moveToFirst()) {
            do {
                String contactId = idCursor.getString(idCursor.getColumnIndex(Contacts._ID));
                String name = idCursor.getString(idCursor.getColumnIndex(Contacts.DISPLAY_NAME_PRIMARY));
                Bitmap photo = getContactPhotoByContactId(contactId);

                PhoneContact contact = new PhoneContact();
                contact.contactId = contactId;
                contact.displayName = name;
                contact.photo = photo;
                contact.phoneNumbers = getContactPhoneNumbersById(contactId);
                contact.emailAddresses = getContactEmailByContactId(contactId);
                contacts.add(contact);
            } while (idCursor.moveToNext());
        }
        idCursor.close();

        return contacts;
    }

    /**
     * 获取所有联系人
     *
     * @return
     */
    public List<PhoneContact> getAllPhoneContacts() {
        List<PhoneContact> contacts = new ArrayList<PhoneContact>();

        ContentResolver contentResolver = context.getContentResolver();
        Cursor idCursor = contentResolver.query(Contacts.CONTENT_URI, new String[]{Contacts._ID, Contacts.DISPLAY_NAME_PRIMARY}, null, null, null);

        if (idCursor.moveToFirst()) {
            do {
                String contactId = idCursor.getString(idCursor.getColumnIndex(Contacts._ID));
                String name = idCursor.getString(idCursor.getColumnIndex(Contacts.DISPLAY_NAME_PRIMARY));
                Bitmap photo = getContactPhotoByContactId(contactId);

                PhoneContact contact = new PhoneContact();
                contact.contactId = contactId;
                contact.displayName = name;
                contact.photo = photo;
                contact.phoneNumbers = getContactPhoneNumbersById(contactId);
                contact.emailAddresses = getContactEmailByContactId(contactId);
                contacts.add(contact);
            } while (idCursor.moveToNext());
        }
        idCursor.close();

        return contacts;
    }

    /**
     * 返回联系人电话号码
     *
     * @param contactId
     * @return
     */
    private Map<String, String> getContactPhoneNumbersById(String contactId) {
        Map<String, String> phoneNumbers = new HashMap<String, String>();

        Cursor phoneCursor = context.getContentResolver().query(Phone.CONTENT_URI, new String[]{Phone.NUMBER, Phone.TYPE}, Phone.CONTACT_ID + " = ?", new String[]{contactId}, null);
        if (phoneCursor.moveToFirst()) {
            do {
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(Phone.NUMBER));
                String phoneNumberType = phoneCursor.getString(phoneCursor.getColumnIndex(Phone.TYPE));
                phoneNumbers.put(phoneNumberType, phoneNumber);
            } while (phoneCursor.moveToNext());
        }

        phoneCursor.close();
        return phoneNumbers;
    }

    /**
     * 返回联系人邮箱
     *
     * @param contactId
     * @return
     */
    private Map<String, String> getContactEmailByContactId(String contactId) {
        Cursor emailCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, new String[]{Phone.NUMBER, Phone.TYPE}, Phone.CONTACT_ID + " = ?",
                new String[]{contactId}, null);
        Map<String, String> emails = new HashMap<String, String>();
        if (emailCursor.moveToFirst()) {
            do {
                String emailAddress = null;
                String emailType = null;
                emailAddress = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                emailType = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                emails.put(emailType, emailAddress);
            } while (emailCursor.moveToNext());
        }
        emailCursor.close();
        return emails;
    }

}
