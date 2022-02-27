import React from "react";
import style from "./index.module.scss";

const MAX_SIZE = 1048576;
export const AVATAR_DEFAULT = "/avatar.jpg";

export default function Avatar(props: Props) {
  const { src, readonly = false } = props;

  const [url, setUrl] = React.useState<string>(src ?? AVATAR_DEFAULT);

  return (
    <div className={style.personalImage}>
      <label className="label">
        {!readonly && (
          <input
            type="file"
            accept="image/*"
            name="avatar"
            onChange={(e) => {
              const files = e.currentTarget?.files;

              if (!files || !files[0]) {
                return;
              }

              const file = files[0];

              if (file.size > MAX_SIZE) {
                return;
              }

              const objectURL = URL.createObjectURL(file);

              setUrl(objectURL);
            }}
          />
        )}
        <figure className={style.figure}>
          <img src={url} className={style.avatar} alt="avatar" />
          {!readonly && (
            <figcaption className={style.figcaption}>
              <img alt="camera-white" src="/camera-white.png" />
            </figcaption>
          )}
        </figure>
      </label>
    </div>
  );
}

/**
 * Types
 */
interface Props {
  src?: string;
  readonly?: boolean;
}
