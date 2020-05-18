DECLARE  
  l_file      UTL_FILE.FILE_TYPE; 
  l_buffer    RAW(32767); 
  l_amount    BINARY_INTEGER := 32767; 
  l_pos       INTEGER := 1; 
  l_blob      BLOB := :1; 
  l_blob_len  INTEGER; 
  l_dir       VARCHAR2(30) := UPPER(:2);
  l_filename  VARCHAR2(400) := :3;
BEGIN 
  l_blob_len := DBMS_LOB.getlength(l_blob); 
  l_file := UTL_FILE.fopen(l_dir,l_filename,'wb', 32767); 
  WHILE l_pos <= l_blob_len LOOP 
	DBMS_LOB.read(l_blob, l_amount, l_pos, l_buffer); 
	UTL_FILE.put_raw(l_file, l_buffer, TRUE); 
	l_pos := l_pos + l_amount; 
  END LOOP; 	  
  -- Close the file. 
  UTL_FILE.fclose(l_file); 
  EXCEPTION 
  WHEN OTHERS THEN 
    IF UTL_FILE.is_open(l_file) THEN 
	  UTL_FILE.fclose(l_file); 
	END IF; 
	RAISE; 
END; 