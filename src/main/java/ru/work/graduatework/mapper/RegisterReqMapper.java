//package ru.work.graduatework.mapper;
//
//import ru.work.graduatework.Entity.RegisterReq;
//import ru.work.graduatework.dto.RegisterReqDto;
//
//public class RegisterReqMapper {
//
//    public static RegisterReqDto toDto(RegisterReq registerReq) {
//        return new RegisterReqDto(registerReq.getUsername(), registerReq.getPassword(), registerReq.getFirstName(),
//                registerReq.getLastName(), registerReq.getPhone(), registerReq.getRole());
//    }
//
//    public static RegisterReq toEntity(RegisterReqDto registerReqDto) {
//        RegisterReq registerReq = new RegisterReq();
//        registerReq.setUsername(registerReqDto.getUsername());
//        registerReq.setPassword(registerReqDto.getPassword());
//        registerReq.setFirstName(registerReqDto.getFirstName());
//        registerReq.setLastName(registerReqDto.getLastName());
//        registerReq.setPhone(registerReqDto.getPhone());
//        registerReq.setRole(registerReqDto.getRole());
//        return registerReq;
//    }
//
//}
