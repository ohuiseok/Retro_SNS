"use client";

import React, { useState } from "react";
import ProfileImg from "./../common/ProfileImg";
import Image from "next/image";
import Plus from "../../../public/icons/plus.svg";
import Link from "next/link";
import { useWaitingCapsuleList } from "@/hooks/queries/capsule";
import Modal from "@/components/common/Modal";
import TimecaplsuleModal from "./TimecaplsuleModal";
import { CapsuleAztTypes } from "@/types/capsule";

export default function TimecapsuleList() {
  const { data: capsuleList, isLoading } = useWaitingCapsuleList();

  const [isModal, setIsModal] = useState({
    is: false,
    detail: {},
  });

  if (capsuleList) {
    console.log(capsuleList);
  }

  const handleClick = (list: CapsuleAztTypes) => {
    setIsModal({ is: true, detail: list });
  };

  const getDayDiff = (openStart: Date): string => {
    const today = new Date().getTime();
    const start = new Date(openStart).getTime();

    const gap = today - start;
    const day = Math.floor(gap / (1000 * 60 * 60 * 24));
    if (day >= 0) {
      return "D-Day";
    } else {
      return "D" + day;
    }
  };

  return (
    <div className="flex">
      <Link href={"/capsule/create"} className="mr-2">
        <div className="flex flex-col items-center justify-center w-[60px]">
          <div className="flex items-center justify-center bg-pink rounded-full w-[55px] h-[55px]">
            <Image src={Plus} alt="btn" />
          </div>
          <p className="text-sm mt-2">캡슐생성</p>
        </div>
      </Link>
      <div className="flex overflow-x-auto scrollbar-hide ">
        {capsuleList &&
          Array.isArray(capsuleList) &&
          capsuleList.length !== 0 &&
          capsuleList.map((list) => {
            return (
              <div
                key={list.tcSeq}
                onClick={() => handleClick(list)}
                className="flex relative flex-col items-center justify-center shrink-0 mr-2"
              >
                {list.openStatus === "LOCKED" && (
                  <div className="absolute top-0 right-0">
                    <Image
                      src="/icons/lock-gray.svg"
                      alt="icon"
                      width="0"
                      height="0"
                      className="w-full h-auto"
                    />
                  </div>
                )}

                <ProfileImg imgSrc={list.aztImage as string} isAzt />
                <div>{getDayDiff(list.openStart as Date)}</div>
              </div>
            );
          })}
      </div>
      {isModal.is && (
        <Modal
          closeModal={() =>
            setIsModal({
              is: false,
              detail: isModal.detail,
            })
          }
        >
          <TimecaplsuleModal detail={isModal.detail} />
        </Modal>
      )}
    </div>
  );
}
