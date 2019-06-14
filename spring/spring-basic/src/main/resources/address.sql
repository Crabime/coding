#sql("findAllAddress")
  select * from address where member_id = ?;
#end
#sql("findMemberByName")
  select * from member where name = ?;
#end
#sql("deleteAddressByMemberName")
  DELETE FROM address WHERE member_id = (select m.id from member m where m.name = ?);
#end