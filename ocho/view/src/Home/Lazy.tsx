import React, { Suspense } from "react";

const LazyHome = React.lazy(() => import("."));

export default function HomeSuspense() {
  return (
    <Suspense fallback={<div>loading...</div>}>
      <LazyHome />
    </Suspense>
  );
}
