package com.entity;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "STORAGE")
public class Storage {
    private long id;
    private String[] formatsSupported;
    private String storageCountry;
    private long storageSize;
    private String formatsSupportedString;

    @Id
    @SequenceGenerator(name = "STOR_SEQ", sequenceName = "STORAGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STOR_SEQ")
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    @Transient
    public String[] getFormatsSupported() {
        return formatsSupported;
    }

    @Column(name = "STORAGE_COUNTRY")
    public String getStorageCountry() {
        return storageCountry;
    }

    @Column(name = "STORAGE_SIZE")
    public long getStorageSize() {
        return storageSize;
    }

    @Column(name = "FORMATS_SUPPORTED")
    public String getFormatsSupportedString() {
        return formatsSupportedString;
    }

    public void setFormatsSupportedString(String formats) {
        this.formatsSupportedString = formats;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFormatsSupported(String[] formatsSupported) {
        this.formatsSupported = formatsSupported;
    }

    public void setStorageCountry(String storageCountry) {
        this.storageCountry = storageCountry;
    }

    public void setStorageSize(long storageSize) {
        this.storageSize = storageSize;
    }

    public String formatsSupportedToString() {
        String formats = Arrays.toString(formatsSupported);
        return formats.substring(1, formats.length() - 1);
    }

    public String[] formatsSupportedToArray() {
        return formatsSupportedString.split(", ");
    }

    public boolean isFormatSupported(String format) {
        for (String f : formatsSupported) {
            if (f.equals(format))
                return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Storage storage = (Storage) o;

        if (id != storage.id) return false;
        if (storageSize != storage.storageSize) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(formatsSupported, storage.formatsSupported))
            return false;
        return storageCountry != null ? storageCountry.equals(storage.storageCountry) : storage.storageCountry == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + Arrays.hashCode(formatsSupported);
        result = 31 * result + (storageCountry != null ? storageCountry.hashCode() : 0);
        result = 31 * result + (int) (storageSize ^ (storageSize >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", formatsSupported=" + Arrays.toString(formatsSupported) +
                ", storageCountry='" + storageCountry + '\'' +
                ", storageSize=" + storageSize +
                '}';
    }
}
