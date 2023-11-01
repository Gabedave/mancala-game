import { useEffect, useState } from "react";

export default function Pit({ index, value, onClick }) {
  const [disablePit, setDisablePit] = useState(false);

  useEffect(() => {
    setDisablePit(false);
  }, [value]);

  const emptyPit = value === 0;
  const useSmallStones = value > 10;

  return (
    <div
      className={`pit-wrapper${
        emptyPit || disablePit ? " disable-player" : ""
      }`}
    >
      <div>
        Pit {index + 1}: {value}
      </div>
      <div
        className="pit"
        onClick={() => {
          setDisablePit(true);
          onClick(index);
        }}
      >
        {[...Array(value).keys()].map((index) => {
          return (
            <div
              key={index}
              className={useSmallStones ? "small-stone" : "stone"}
            />
          );
        })}
      </div>
    </div>
  );
}
