"use client";
import Textbox from "../common/Textbox";
import Image from "next/image";
import React, { useState } from "react";
import ProfileDropdown from "./ProfileDropdown";
import { useUserList } from "@/hooks/queries/user";
import { useRouter } from "next/navigation";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { logOut, withDraw } from "@/app/api/user";
import { useDispatch } from "react-redux";
import {
  updateAccessToken,
  updateLoginStatus,
  updateUserInfo,
} from "@/store/authSlice";
import { queryKeys } from "@/constants/queryKeys";
import ModalBtn from "../common/ModalBtn";
import { useAppSelector } from "@/hooks/redux";
import { deleteFollow, postBlock } from "@/app/api/friend";
import { KEYS } from "@/constants/queryKeys";

interface ProfileMainProps {
  userSeq: number;
}

function ProfileMain({ userSeq }: ProfileMainProps) {
  const { data: profileData, isLoading } = useUserList(userSeq);
  console.log(profileData, "profileData");
  const state = useAppSelector((state) => state);
  const myUserSeq = state.auth.userInfo?.userSeq;

  const [isModal, setIsModal] = useState(false);
  const router = useRouter();
  const pushToProfileEdit = () => {
    router.push("/profile/edit");
  };

  const queryClient = useQueryClient();
  const dispatch = useDispatch();
  const deleteUserInfo = () => {
    dispatch(updateLoginStatus(false));
    dispatch(updateAccessToken(""));
    dispatch(updateUserInfo(null));
    router.push("/");
  };

  const useLogOutMutation = useMutation({
    mutationFn: () => logOut(),
    onSuccess: () => {
      queryClient.invalidateQueries(queryKeys.user.userList(+userSeq!));
      alert("로그아웃 완료");
      deleteUserInfo();
    },
  });

  const useWithDrawMutation = useMutation({
    mutationFn: () => withDraw(),
    onSuccess: () => {
      queryClient.invalidateQueries(queryKeys.user.userList(+userSeq!));
      alert("회원탈퇴 되었어요 ㅠㅠ");
      deleteUserInfo();
    },
  });

  const handleClickLogOut = () => {
    useLogOutMutation.mutate();
  };
  const handleYesWithDraw = () => {
    useWithDrawMutation.mutate();
  };

  const useDeleteFollowMutation = useMutation({
    mutationFn: () => deleteFollow(userSeq),
    onSuccess: () => {
      queryClient.invalidateQueries(KEYS.friend);
    },
  });

  const usePostBlockMutation = useMutation({
    mutationFn: () => postBlock(userSeq),
    onSuccess: () => {
      queryClient.invalidateQueries(KEYS.friend);
    },
  });

  const handleClickDeleteFollow = () => {
    useDeleteFollowMutation.mutate();
  };

  const handleClickBlock = () => {
    usePostBlockMutation.mutate();
  };

  return (
    <>
      <div className={`box-content w-full h-full bg-yellow p-1`}>
        <div className="flex flex-row">
          <div className="mt-1 ml-1 mr-1">
            <img
              src={profileData?.profileImage}
              width={124}
              height={181}
              alt="example"
            />
          </div>
          {/* 유저 본인일 때 이 드롭다운이 보이게 하기 */}
          <div>
            <div className="flex pl-5 gap-10">
              {profileData?.identify}

              {userSeq === myUserSeq ? (
                <ProfileDropdown
                  firstText="프로필 편집"
                  firstFunc={pushToProfileEdit}
                  secondText="로그아웃"
                  secondFunc={handleClickLogOut}
                  thirdText="탈퇴하기"
                  thirdFunc={() => setIsModal(true)}
                />
              ) : profileData?.friend === true ? (
                <ProfileDropdown
                  firstText="친구 끊기"
                  firstFunc={handleClickDeleteFollow}
                  secondText="차단하기"
                  secondFunc={handleClickBlock}
                />
              ) : (
                <ProfileDropdown
                  firstText="차단하기"
                  firstFunc={handleClickBlock}
                />
              )}
            </div>
            <Textbox
              icon="/icons/edit.svg"
              alt="pretty"
              text="수식어"
              maintext={profileData?.modifier}
            />
            <Textbox
              icon="/icons/avatar.svg"
              alt="nickname"
              text="닉네임"
              maintext={profileData?.nickname}
            />
            <Textbox
              icon="/icons/user.svg"
              alt="visitor"
              text="방문자"
              maintext={profileData?.visitCount}
            />
          </div>
        </div>
        <div className="border-2 border-black m-1 h-[92px] shadow-lg pb-2">
          <div className="border-black border-b-2">자기소개 한 마디</div>
          <div className="">{profileData?.introduce}</div>
        </div>
      </div>
      {isModal && (
        <ModalBtn
          yesFunc={handleYesWithDraw}
          closeModal={() => setIsModal(false)}
          text="정말 탈퇴할까요 ㅠㅠ?"
        />
      )}
    </>
  );
}

export default ProfileMain;
