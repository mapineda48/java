import React, { Suspense } from "react";
import Loading from "../Loading";

const LazyApp = React.lazy(() => import("."));

export default function AppSuspense() {
  return (
    <Suspense fallback={<Loading />}>
      <LazyApp />
    </Suspense>
  );
}
