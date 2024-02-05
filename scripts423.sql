
SELECT jsonb_build_object('name_student', st."name", 'age', age,
 			  'faculty_name', f."name", 'color', f.color) AS student
 FROM student st join faculty f on st.faculty_id = f.id limit 2;
/*
{"age": 18, "color": "red", "faculty_name": "Gryffindor", "name_student": "Tom Riddle"}
{"age": 18, "color": "red", "faculty_name": "Gryffindor", "name_student": "Percy Weasley"}
*/


select jsonb_build_object('name', st."name", 'age', st.age,
	  'path', av.file_path, 'size', av.file_size) res from student st join avatar av on st.id = av.id;

/*
{"age": 18, "name": "Tom Riddle", "path": "avatars/Tom Riddle.png", "size": 54563}
{"age": 18, "name": "Percy Weasley", "path": "avatars/Percy Weasley.png", "size": 48338}
{"age": 18, "name": "Percy Weasley", "path": "avatars/Percy Weasley.png", "size": 63770}
{"age": 17, "name": "Fred Weasley", "path": "avatars/Fred Weasley.png", "size": 71334}
{"age": 17, "name": "George Weasley", "path": "avatars/George Weasley.png", "size": 81880}
{"age": 17, "name": "Aberforth Dumbledore", "path": "avatars/Aberforth Dumbledore.png", "size": 47230}
*/