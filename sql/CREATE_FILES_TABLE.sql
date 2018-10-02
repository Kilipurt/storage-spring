CREATE TABLE FILES (
    ID NUMBER PRIMARY KEY,
    NAME NVARCHAR2(10),
    FORMAT NVARCHAR2(20),
    FILE_SIZE NUMBER,
    STORAGE NUMBER,
    CONSTRAINT STORAGE_FK FOREIGN KEY(STORAGE) REFERENCES STORAGE(ID) ON DELETE SET NULL
);