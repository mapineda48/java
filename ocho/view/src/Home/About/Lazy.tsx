import React, { Suspense } from "react";
import Loading from "../../Loading";

const LazyAbout = React.lazy(() => import("."));

export default function AboutSuspense() {
  return (
    <Suspense fallback={<Loading />}>
      <LazyAbout />
    </Suspense>
  );
}
