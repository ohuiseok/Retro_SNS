"use client";

import Btn from "@/components/common/Btn";
import ProfileImg from "@/components/common/ProfileImg";
import Textbox from "@/components/common/Textbox";
import { LOCAL } from "@/constants/localUrl";
import { API } from "@/constants/queryKeys";
import { useAztInfo } from "@/hooks/queries/azt";
import { SlugProps } from "@/types/common";
import Image from "next/image";
import Link from "next/link";
import { notFound } from "next/navigation";
import React from "react";

function AztInfoPage({ params: { slug } }: SlugProps) {
  const {
    data: aztInfoData,
    isLoading: isAztInfoLoading,
    isError,
  } = useAztInfo(slug);

  const handleClickInvite = () => {};
  const handleClickWithdraw = () => {};

  if (isAztInfoLoading) {
    return <div>로딩중 ㄱ-...</div>;
  }

  if (!aztInfoData) {
    return notFound();
  }

  if (aztInfoData) {
    const { aztSeq, name, createdAt, image, members, chatSeq } = aztInfoData;
    return (
      <div className="flex flex-col gap-y-1">
        <div>
          <div className="flex justify-between mb-1">
            <p>아지트 이름</p>
            <div className="flex gap-x-2">
              <Link href={`/${API.chat}/${chatSeq}`}>
                <Image
                  src="/icons/chat.svg"
                  alt="채팅 아이콘"
                  width={20}
                  height={15}
                />
              </Link>
              <Link href={`/${API.azt}/${aztSeq}/edit`}>
                <Image
                  src="/icons/edit.svg"
                  alt="편집 아이콘"
                  width={18}
                  height={18}
                />
              </Link>
            </div>
          </div>
          <Textbox
            icon="/icons/users.svg"
            alt="아지트 아이콘"
            maintext={name}
            bgColor="pink"
          />
        </div>
        <div>
          <p>아지트 생성일</p>
          <Textbox
            icon="/icons/calendar.svg"
            alt="날짜 아이콘"
            maintext={createdAt}
          />
        </div>
        <div>
          <p>아지트 대표 사진</p>
          <img
            src={image}
            alt={name}
            className="rounded border-2 border-black mt-2"
          />
        </div>
        <div className="flex flex-col gap-y-2 my-2">
          <p>아지트 멤버</p>
          <div className="flex text-center gap-x-3 justify-center">
            {members.map(({ userSeq, profileImage }) => (
              <div key={userSeq}>
                <Link href={`/${LOCAL.profile}/${userSeq}`}>
                  <ProfileImg imgSrc={profileImage} />
                </Link>
              </div>
            ))}
          </div>
          <div className="my-3 mx-auto">
            {/* TODO: 멤버 초대 링크 연결 */}
            <Btn bgColor="blue" text="멤버 초대" btnFunc={handleClickInvite} />
          </div>
        </div>
        <div className="text-right mt-16">
          <Btn
            bgColor="blue"
            className="bg-neutral-200"
            text="탈퇴하기"
            btnFunc={handleClickWithdraw}
          />
        </div>
      </div>
    );
  }
}

export default AztInfoPage;
