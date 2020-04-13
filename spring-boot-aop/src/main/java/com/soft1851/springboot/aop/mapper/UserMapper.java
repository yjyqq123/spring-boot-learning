package com.soft1851.springboot.aop.mapper;

import com.soft1851.springboot.aop.entity.Student;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.sql.SQLException;
import java.util.List;

public interface UserMapper {
    /**
     * 查询所有
     * @return List</Map>
     */
    @Select("SELECT * FROM t_student")
    @Results({
            @Result(id=true,property="id",column="id",javaType = Integer.class),
            @Result(property="name",column="name",javaType = String.class),
            @Result(property = "role",column="role",javaType=Boolean.class)
    })
    List<Student> selectAll();

    /**
     * 根据Id查询
     * @param id
     * @return
     * @throws SQLException
     */
    @Select("SELECT role FROM t_student WHERE id=#{id}")
    Student selectAdminById(String id) throws SQLException;
}
