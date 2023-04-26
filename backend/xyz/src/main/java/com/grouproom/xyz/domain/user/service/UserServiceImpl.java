package com.grouproom.xyz.domain.user.service;

import com.grouproom.xyz.domain.user.dto.response.FriendshipResponse;
import com.grouproom.xyz.domain.user.dto.response.ModifierResponse;
import com.grouproom.xyz.domain.user.dto.response.ProfileResponse;
import com.grouproom.xyz.domain.user.entity.Modifier;
import com.grouproom.xyz.domain.user.entity.User;
import com.grouproom.xyz.domain.user.entity.UserModifier;
import com.grouproom.xyz.domain.user.repository.ModifierRepository;
import com.grouproom.xyz.domain.user.repository.UserModifierRepository;
import com.grouproom.xyz.domain.user.repository.UserRepository;
import com.grouproom.xyz.global.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * packageName    : com.grouproom.xyz.domain.user.service
 * fileName       : UserServiceImpl
 * author         : SSAFY
 * date           : 2023-04-25
 * description    :
 * <p>
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * <p>
 * 2023-04-25        SSAFY       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserModifierRepository userModifierRepository;
    private final ModifierRepository modifierRepository;

    @Override
    @Transactional
    public void removeUser(Long userSequence) {
        User user = userRepository.findBySequence(userSequence);
        user.changeIsDeleted(true);
    }

    @Override
    public List<ModifierResponse> findModifierByUserSequence(Long userSeq) {
        return userRepository.selectModifierByUserSeq(userSeq);
    }

    @Override
    @Transactional
    public ProfileResponse findProfileByUserSeq(Long fromUserSeq,Long toUserSeq){
        User fromUser = userRepository.getReferenceById(fromUserSeq);
        User toUser = userRepository.findBySequence(toUserSeq);

        toUser.changeVisitCount(toUser.getVisitCount()+1);

        ProfileResponse profileResponse =  userRepository.selectProfileByUserSeq(toUser)
                        .orElseThrow( () -> new ErrorResponse(HttpStatus.BAD_REQUEST,"해당 유저는 없는 유저입니다."));
        profileResponse.setFriend(userRepository.selectFriendshipByUserSeq(fromUser,toUser).orElse(null));
        profileResponse.setBgms(userRepository.selectBgmByUserSeq(toUser));

        return profileResponse;
    }

    @Override
    @Transactional
    public ProfileResponse findProfileByUserSeq(Long myUserSeq) {
        User myUser = userRepository.findBySequence(myUserSeq);

        myUser.changeVisitCount(myUser.getVisitCount()+1);

        ProfileResponse profileResponse =  userRepository.selectProfileByUserSeq(myUser)
                .orElseThrow( () -> new ErrorResponse(HttpStatus.BAD_REQUEST,"내 정보를 읽을 수 없습니다. 문의 부탁드립니다."));
        profileResponse.setBgms(userRepository.selectBgmByUserSeq(myUser));

        return profileResponse;
    }

    @Override
    @Transactional
    public void modifyProfile(Long userSeq, String nickname, String profileImagePath, String backgroundImagePath, String introduce, Long modifierSeq) {
        User user = userRepository.findBySequence(userSeq);
        user.changeProfile(nickname, profileImagePath, backgroundImagePath, introduce);

        if(modifierSeq!=null){
            UserModifier userModifier = userModifierRepository.findByUser_SequenceAndModifier_Sequence(userSeq,modifierSeq);
            if(userModifier == null) {
                throw new ErrorResponse(HttpStatus.BAD_REQUEST,"소유하지 않은 수식어를 선택했습니다.");
            }else{
                userModifier.changeIsSelected(true);
            }
        }
    }
}
