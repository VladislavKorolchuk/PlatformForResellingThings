//package ru.work.graduatework.mapper;
//
//import ru.work.graduatework.Entity.Users;
//import ru.work.graduatework.dto.UserDto;
//
//public class UsersMapper1 {
//
//    public static UserDto toDto(Users users) {
//        return new UserDto(users.getId(), users.getFirstName(), users.getLastName(), users.getPhone(),
//                users.getEmail(), users.getRegDate(), users.getCity(), users.getImage());
//        //, users.getAdsCollection()
//    }
//
//    public static Users toEntity(UserDto userDto) {
//        Users users = new Users();
//        users.setId(userDto.getId());
//        users.setFirstName(userDto.getFirstName());
//        users.setLastName(userDto.getLastName());
//        users.setPhone(userDto.getPhone());
//        users.setEmail(userDto.getEmail());
//        users.setRegDate(userDto.getRegDate());
//        users.setCity(userDto.getCity());
//        users.setImage(userDto.getImage());
//       // users.setAdsCollection(userDto.getAdsCollection());
//        return users;
//    }
//}
